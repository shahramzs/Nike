package com.example.nike.base

import androidx.annotation.RequiresApi
import com.example.nike.R
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber

class NikeExceptionMapper {

    companion object{
        @RequiresApi(34)
        fun map(throwable: Throwable):NikeException{
            if(throwable is HttpException){
                try{
                    val errorJsonObject = JSONObject(throwable.response()?.errorBody()!!.string())
                    val errorMessage = errorJsonObject.getString("detail")
                    return NikeException(if(throwable.code() == 401) NikeException.Type.AUTH else NikeException.Type.SIMPLE, serverMessage = errorMessage)
                }catch(exception:Exception){
                    Timber.tag("NikeException").e(exception)
                }
            }
            return NikeException(NikeException.Type.SIMPLE, R.string.unknown_error)

        }
    }
}