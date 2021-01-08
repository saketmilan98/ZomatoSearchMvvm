package com.tcp.zomatosearch.data.model

data class CuisineRestaurantDataClass(val cuisines: String,
                                      val restaurants: List<Restaurant>)

data class RootDataClass(
    val restaurants: List<Restaurant>,
    val results_found: Int,
    val results_shown: Int,
    val results_start: Int
)

data class Restaurant(
    val restaurant: RestaurantX
)

data class RestaurantX(
    val R: R,
    val all_reviews: AllReviews,
    val all_reviews_count: Int,
    val apikey: String,
    val average_cost_for_two: Int,
    val book_again_url: String,
    val book_form_web_view_url: String,
    val cuisines: String,
    val currency: String,
    val deeplink: String,
    val establishment: List<String>,
    val establishment_types: List<Any>,
    val events_url: String,
    val featured_image: String,
    val has_online_delivery: Int,
    val has_table_booking: Int,
    val highlights: List<String>,
    val id: String,
    val include_bogo_offers: Boolean,
    val is_book_form_web_view: Int,
    val is_delivering_now: Int,
    val is_table_reservation_supported: Int,
    val is_zomato_book_res: Int,
    val location: Location,
    val medio_provider: Boolean,
    val menu_url: String,
    val mezzo_provider: String,
    val name: String,
    val offers: List<Any>,
    val opentable_support: Int,
    val order_deeplink: String,
    val order_url: String,
    val phone_numbers: String,
    val photo_count: Int,
    val photos_url: String,
    val price_range: Int,
    val store_type: String,
    val switch_to_order_menu: Int,
    val thumb: String,
    val timings: String,
    val url: String,
    val user_rating: UserRating
)

data class R(
    val has_menu_status: HasMenuStatus,
    val is_grocery_store: Boolean,
    val res_id: Int
)

data class AllReviews(
    val reviews: List<Review>
)

data class Location(
    val address: String,
    val city: String,
    val city_id: Int,
    val country_id: Int,
    val latitude: String,
    val locality: String,
    val locality_verbose: String,
    val longitude: String,
    val zipcode: String
)

data class UserRating(
    val aggregate_rating: Any,
    val custom_rating_text: String,
    val custom_rating_text_background: String,
    val rating_color: String,
    val rating_obj: RatingObj,
    val rating_text: String,
    val rating_tool_tip: String,
    val votes: Int
)

data class HasMenuStatus(
    val delivery: Any,
    val takeaway: Int
)

data class Review(
    val review: List<Any>
)

data class RatingObj(
    val bg_color: BgColor,
    val title: Title
)

data class BgColor(
    val tint: String,
    val type: String
)

data class Title(
    val text: String
)