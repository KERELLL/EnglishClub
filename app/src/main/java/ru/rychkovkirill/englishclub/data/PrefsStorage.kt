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
        val isAdmin = sharedPreferences.getString(ROLE_KEY, null)
        val firstName = sharedPreferences.getString(NAME_KEY, null)
        val lastName = sharedPreferences.getString(LAST_NAME_KEY, null)
        val mediaLink = sharedPreferences.getString(MEDIA_KEY, null)
        val user = mutableListOf<String>()
        if(!name.isNullOrBlank()
            && !token.isNullOrBlank()
            && !isAdmin.isNullOrBlank()
            && !email.isNullOrBlank()
            && !isAdmin.isNullOrBlank()
            && !email.isNullOrBlank()
            && !firstName.isNullOrBlank()
            && !lastName.isNullOrBlank()
            && mediaLink != null){
            user.add(token)
            user.add(name)
            user.add(isAdmin)
            user.add(email)
            user.add(firstName)
            user.add(lastName)
            user.add(mediaLink)
            return user
        }
        return null
    }

    fun saveToSharedPreferences(token: String, username: String, isAdmin: String, email: String, firstName: String, lastName: String, media_link: String){
        if(username.isNotEmpty()){
            sharedPreferences.edit()
                .putString(TOKEN_KEY, token)
                .putString(USERNAME_KEY, username)
                .putString(EMAIL_KEY, email)
                .putString(ROLE_KEY, isAdmin)
                .putString(NAME_KEY, firstName)
                .putString(LAST_NAME_KEY, lastName)
                .putString(MEDIA_KEY, media_link)
                .apply()
        }else{
            sharedPreferences.edit()
                .remove(USERNAME_KEY)
                .remove(TOKEN_KEY)
                .remove(ROLE_KEY)
                .remove(EMAIL_KEY)
                .remove(NAME_KEY)
                .remove(LAST_NAME_KEY)
                .remove(MEDIA_KEY)
                .apply()
        }
    }

    companion object {
        private const val USERNAME_KEY = "username"
        private const val EMAIL_KEY = "email"
        private const val TOKEN_KEY = "token"
        private const val ROLE_KEY = "role"
        private const val NAME_KEY = "firstName"
        private const val LAST_NAME_KEY = "lastName"
        private const val MEDIA_KEY = "media_link"
    }
}