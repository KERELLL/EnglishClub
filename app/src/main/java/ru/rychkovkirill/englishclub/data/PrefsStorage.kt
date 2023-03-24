package ru.rychkovkirill.englishclub.data

import android.content.Context
import javax.inject.Inject

class PrefsStorage @Inject constructor(
    context: Context
) {
    private val sharedPreferences = context.getSharedPreferences(
        "User data preferences",
        Context.MODE_PRIVATE
    )

    fun getUser() : List<String>?{
        val email = sharedPreferences.getString(EMAIL_KEY, null)
        val name = sharedPreferences.getString(USERNAME_KEY, null)
        val token = sharedPreferences.getString(TOKEN_KEY, null)
        val user = mutableListOf<String>()
        if(!name.isNullOrBlank()
            && !token.isNullOrBlank()){
            user.add(name)
            user.add(token)
            return user
        }
        return null
    }

    fun saveToSharedPreferences(username: String, token: String){
        if(username.isNotBlank() ){
            sharedPreferences.edit()
                .putString(USERNAME_KEY, username)
                .putString(TOKEN_KEY, token)
                .apply()
        }else{
            sharedPreferences.edit()
                .remove(USERNAME_KEY)
                .remove(TOKEN_KEY)
                .apply()
        }
    }

    companion object {
        private const val USERNAME_KEY = "username"
        private const val EMAIL_KEY = "email"
        private const val TOKEN_KEY = "token"
    }
}