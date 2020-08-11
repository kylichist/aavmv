package com.github.kylichist.aavmv.vk

import com.github.kylichist.aavmv.util.formGetUserLink
import com.github.kylichist.aavmv.util.get

fun getName(id: String, userToken: String): String? =
    with(get(formGetUserLink(id, userToken))) {
        if (has("response"))
            with(
                getJSONArray("response")
                    .getJSONObject(0)
            ) {
                return "${getString("first_name")} ${getString("last_name")}"
            }
        else return null
    }
