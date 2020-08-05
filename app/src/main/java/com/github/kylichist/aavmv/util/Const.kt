package com.github.kylichist.aavmv.util

const val GET_TOKEN_LINK = "https://oauth.vk.com/authorize?client_id=$APP_ID&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=offline,messages&response_type=token"
const val GET_TOKEN_LINK_DEBUG = "https://oauth.vk.com/authorize?client_id=$APP_ID&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=offline&response_type=token"
const val GET_PROFILE_INFO_LINK = "https://api.vk.com/method/users.get?user_id="

const val ACCESS_TOKEN = "&access_token="
const val VERSION = "&v=5.122"

const val SHARED_PREFERENCES_NAME = "settings"