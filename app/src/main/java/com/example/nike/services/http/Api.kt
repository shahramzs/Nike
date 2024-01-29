package com.example.nike.services.http

class Api {

    companion object{
        const val ROOT_URL:String = "http://192.168.1.2:8000/"

        const val PRODUCT_URL: String = "product/"
        const val BANNER_URL: String = "banner/"
        const val COMMENT_URL:String = "get_comment/"
        const val ADD_TO_CART:String = "add_to_cart/"
        const val REGISTER:String = "register/"
        const val AUTH:String = "auth/"
        const val DELETE_CART = "delete_cart/"
        const val LIST_CART:String = "list_cart/"
        const val CHANGE_COUNT:String = "change_count/"
        const val GET_CART_ITEM_COUNT:String = "getCartItemCount/"
        const val SHIPPING:String = "shipping/"
    }

}