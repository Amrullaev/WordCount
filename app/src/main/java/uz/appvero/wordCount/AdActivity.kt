package uz.appvero.wordCount

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import uz.appvero.wordCount.databinding.ActivityAdBinding

class AdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdBinding
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this)

        loadBanner()
        loadInterstitial()
        loadRewarded()

        binding.btnShowInterstitial.setOnClickListener {
            if (interstitialAd != null) {
                interstitialAd?.show(this)
            } else {
                binding.statusText.text = "Interstitial hali yuklanmagan"
                loadInterstitial()
            }
        }

        binding.btnShowRewarded.setOnClickListener {
            if (rewardedAd != null) {
                rewardedAd?.show(this) { rewardItem ->
                    binding.statusText.text =
                        "Mukofot berildi: ${rewardItem.amount} ${rewardItem.type}"
                }
            } else {
                binding.statusText.text = "Rewarded hali yuklanmagan"
                loadRewarded()
            }
        }
    }

    private fun loadBanner() {
        val adRequest = AdRequest.Builder().build()
        binding.bannerAd.loadAd(adRequest)
    }

    private fun loadInterstitial() {
        binding.statusText.text = "Interstitial yuklanmoqda..."
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            "ca-app-pub-9396367590188441/1782079122",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    binding.statusText.text = "Interstitial tayyor"
                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            interstitialAd = null
                            binding.statusText.text = "Interstitial yopildi"
                            loadInterstitial()
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            interstitialAd = null
                            binding.statusText.text = "Xato: ${adError.message}"
                        }
                    }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    interstitialAd = null
                    binding.statusText.text = "Interstitial xato: ${loadAdError.message}"
                }
            }
        )
    }

    private fun loadRewarded() {
        binding.statusText.text = "Rewarded yuklanmoqda..."
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            this,
            "ca-app-pub-9396367590188441/8155915788",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                    binding.statusText.text = "Rewarded tayyor"
                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            rewardedAd = null
                            binding.statusText.text = "Rewarded yopildi"
                            loadRewarded()
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            rewardedAd = null
                            binding.statusText.text = "Xato: ${adError.message}"
                        }
                    }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    rewardedAd = null
                    binding.statusText.text = "Rewarded xato: ${loadAdError.message}"
                }
            }
        )
    }

    override fun onDestroy() {
        binding.bannerAd.destroy()
        super.onDestroy()
    }

    override fun onPause() {
        binding.bannerAd.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.bannerAd.resume()
    }
}
