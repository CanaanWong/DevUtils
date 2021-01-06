package afkt.app.ui.fragment

import afkt.app.R
import afkt.app.base.BaseFragment
import afkt.app.base.Constants
import afkt.app.databinding.FragmentSettingBinding
import afkt.app.ui.dialog.AppSortDialog
import afkt.app.ui.dialog.QuerySuffixDialog
import afkt.app.utils.AppListUtils
import afkt.app.utils.ProjectUtils
import afkt.app.utils.QuerySuffixUtils
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import dev.utils.app.ResourceUtils
import dev.utils.app.share.SharedUtils
import dev.utils.app.toast.ToastTintUtils

class SettingFragment : BaseFragment<FragmentSettingBinding>() {

    override fun baseContentId() = R.layout.fragment_setting

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.vidFsAppsortLinear.setOnClickListener { AppSortDialog(viewModel, context).show() }
        binding.vidFsScanapkLinear.setOnClickListener { QuerySuffixDialog(context).show() }
        binding.vidFsResetLinear.setOnClickListener {
            SharedUtils.put(Constants.Key.KEY_APP_SORT, 0)
            QuerySuffixUtils.reset() // 清空后缀
            AppListUtils.reset() // 清空应用列表
            selectAppSort() // 重置排序文案
            ToastTintUtils.success(ResourceUtils.getString(R.string.str_reset_desetting_suc))
        }
        viewModel.appSort.observe(viewLifecycleOwner, Observer {
            selectAppSort()
        })
        selectAppSort()
    }

    /**
     * 设置选择排序文案
     */
    private fun selectAppSort() {
        binding.vidFsAppsortTv.text =
            ResourceUtils.getStringArray(R.array.array_app_sort)!![ProjectUtils.getAppSortType()]
    }
}