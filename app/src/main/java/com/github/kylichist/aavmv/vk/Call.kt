package com.github.kylichist.aavmv.vk

import com.github.kylichist.aavmv.util.*

fun getName(id: String, userToken: String): Response =
    with(get(formGetUserLink(id, userToken))) {
        if (has("response"))
            with(
                getJSONArray("response")
                    .getJSONObject(0)
            ) {
                return Successful("${getString("first_name")} ${getString("last_name")}")
            }
        else return Fail
    }