package com.vitantonio.nagauzzi.sansuukids.model

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import sansuukids.composeapp.generated.resources.Res
import sansuukids.composeapp.generated.resources.result_medal_bronze
import sansuukids.composeapp.generated.resources.result_medal_emoji_bronze
import sansuukids.composeapp.generated.resources.result_medal_emoji_gold
import sansuukids.composeapp.generated.resources.result_medal_emoji_nothing
import sansuukids.composeapp.generated.resources.result_medal_emoji_silver
import sansuukids.composeapp.generated.resources.result_medal_emoji_star
import sansuukids.composeapp.generated.resources.result_medal_gold
import sansuukids.composeapp.generated.resources.result_medal_nothing
import sansuukids.composeapp.generated.resources.result_medal_silver
import sansuukids.composeapp.generated.resources.result_medal_star

/**
 * クイズ結果に応じて獲得できるメダルの種類。
 */
@Serializable
internal sealed interface Medal {

    @Serializable
    data object Nothing : Medal

    @Serializable
    data object Gold : Medal

    @Serializable
    data object Silver : Medal

    @Serializable
    data object Bronze : Medal

    @Serializable
    data object Star : Medal
}

internal val Medal.emojiRes: StringResource
    get() = when (this) {
        Medal.Nothing -> Res.string.result_medal_emoji_nothing
        Medal.Gold -> Res.string.result_medal_emoji_gold
        Medal.Silver -> Res.string.result_medal_emoji_silver
        Medal.Bronze -> Res.string.result_medal_emoji_bronze
        Medal.Star -> Res.string.result_medal_emoji_star
    }

internal val Medal.descriptionRes: StringResource
    get() = when (this) {
        Medal.Nothing -> Res.string.result_medal_nothing
        Medal.Gold -> Res.string.result_medal_gold
        Medal.Silver -> Res.string.result_medal_silver
        Medal.Bronze -> Res.string.result_medal_bronze
        Medal.Star -> Res.string.result_medal_star
    }
