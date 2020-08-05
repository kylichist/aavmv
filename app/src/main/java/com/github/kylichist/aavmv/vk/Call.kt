package com.github.kylichist.aavmv.vk

import com.github.kylichist.aavmv.util.ACCESS_TOKEN
import com.github.kylichist.aavmv.util.GET_PROFILE_INFO_LINK
import com.github.kylichist.aavmv.util.VERSION
import com.github.kylichist.aavmv.util.get

fun getName(id: String, userToken: String): String {
    val result =
        get(GET_PROFILE_INFO_LINK + id + VERSION + ACCESS_TOKEN + userToken)
            .getJSONArray("response")
            .getJSONObject(0)
    return "${result.getString("first_name")} ${result.getString("last_name")}"
}