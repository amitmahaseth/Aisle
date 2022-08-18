import android.content.Context
import android.content.SharedPreferences


class SharedPref(private var context: Context) {
    private var pref: SharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)


    fun saveString(key: String, value: String) {
        pref.edit().putString(key, value).apply()
    }


    fun getString(key: String): String {
        return pref.getString(key, "").toString()
    }


}