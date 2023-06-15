package com.frogobox.webview.ui.main

import android.os.Bundle
import com.frogobox.sdk.ext.gone
import com.frogobox.sdk.ext.showLogD
import com.frogobox.sdk.ext.visible
import com.frogobox.webview.ConfigApp
import com.frogobox.webview.ConfigApp.URL_LINK_WEBSITE
import com.frogobox.webview.common.callback.AdCallback
import com.frogobox.webview.common.callback.WebViewCallback
import com.frogobox.webview.common.core.BaseActivity
import com.frogobox.webview.common.ext.loadUrlExt
import com.frogobox.webview.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun setupViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreateExt(savedInstanceState: Bundle?) {
        super.onCreateExt(savedInstanceState)
        setupFlagAd()
    }

    private fun setupFlagAd() {
        if (ConfigApp.Flag.IS_USING_AD_INTERSTITIAL) {
            setupAd()
        } else {
            setupUI()
        }
    }

    private fun setupAd() {
        showInterstitial(object : AdCallback {

            override fun onShowProgress() {
                binding.containerProgressView.progressView.visible()
            }

            override fun onHideProgress() {
                binding.containerProgressView.progressView.gone()
            }

            override fun onFinish() {
                setupUI()
            }

            override fun onFailed() {
                setupUI()
            }

        })
    }

    private fun setupUI() {
        binding.apply {

            mainWebview.loadUrlExt(URL_LINK_WEBSITE, object : WebViewCallback {

                override fun onShowProgress() {
                    containerProgressView.progressView.visible()
                }

                override fun onHideProgress() {
                    containerProgressView.progressView.gone()
                }

                override fun onFinish() {
                    containerFailedView.failedView.gone()
                    if (ConfigApp.Flag.IS_USING_AD_BANNER) {
                        showAdBanner(adsView.adsPhoneTabSpecialSmartBanner)
                    } else {
                        adsView.adsPhoneTabSpecialSmartBanner.gone()
                    }
                }

                override fun onFailed() {
                    // Activate this if you want failed view
                    // containerFailedView.failedView.visible()
                }

            })
        }
    }

}