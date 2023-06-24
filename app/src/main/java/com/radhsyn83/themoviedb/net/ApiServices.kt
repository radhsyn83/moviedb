package com.radhsyn83.themoviedb.net


import com.radhsyn83.themoviedb.data.models.DetailModel
import com.radhsyn83.themoviedb.data.models.GenresModel
import com.radhsyn83.themoviedb.data.models.MoviesModel
import com.radhsyn83.themoviedb.data.models.ReviewsModel
import com.radhsyn83.themoviedb.data.models.TrailersModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiServices {

    @GET("genre/movie/list")
    fun genres(): Observable<GenresModel>

    @GET("discover/movie")
    fun movies(
        @Query("with_genres") id: Int,
        @Query("page") page: Int,
    ):  Observable<MoviesModel>

    @GET("movie/{id}")
    fun detail(
        @Path("id") id: Int,
    ): Observable<DetailModel>

    @GET("movie/{id}/reviews")
    fun reviews(
        @Path("id") id: Int,
    ): Observable<ReviewsModel>

    @GET("movie/{id}/trailers")
    fun trailers(
        @Path("id") id: Int,
    ): Observable<TrailersModel>
//
//    @PUT("member/profile")
//    @FormUrlEncoded
//    fun profileUpdate(
//        @Field("name") name: String,
//        @Field("birthday") birthday: String,
//        @Field("job") job: String,
//        @Field("phone") phone: String,
//        @Field("email") email: String,
//        @Field("gender") gender: String,
//        @Field("address") address: String,
//        @Field("photo") photo: String,
//        @Field("address_id") addressIdd: String,
//    ): Observable<DefaultModel>
//
//    @POST("login")
//    fun login(
//        @Body Raw: HashMap<String, Any>
//    ): Observable<AuthModel>
//
//    @POST("member/update-password")
//    @FormUrlEncoded
//    fun changePassword(
//        @Field("old_password") oldPassword: String,
//        @Field("new_password") newPassword: String
//    ): Observable<DefaultModel>
//
//    @POST("invoice/confirm-payment")
//    @FormUrlEncoded
//    fun confirmPayment(
//        @Field("invoice_id") invoiceId: String,
//        @Field("date_payment") datePayment: String,
//        @Field("file") file: String,
//        @Field("amount_confirmation") amount: String
//    ): Observable<DefaultModel>
//
//    @GET("homepage/product")
//    fun homeProduct(): Observable<HomeProductModel>
//
//    @GET("homepage/banner")
//    fun homeBanner(): Observable<BannerModel>
//
//    @GET("product/schedule/product")
//    fun productSchedule(): Observable<ProductScheduleModel>
//
//    @GET("product/slug/{slug}")
//    fun detailProduct(@Path("slug") slug: String): Observable<ProductDetailModel>
//
//    @GET("cart/checkout")
//    fun cartCheckout(): Observable<CartCheckoutModel>
//
//    @GET("category")
//    fun categories(): Observable<CategoriesModel>
//
//    @GET("order/pending")
//    fun payLater(): Observable<PayLaterModel>
//
//    @POST("order")
//    fun checkout(
//        @Body Raw: HashMap<String, Any>
//    ): Observable<CheckoutModel>
//
//    @POST("order/invoice")
//    fun payLaterPay(
//        @Body Raw: HashMap<String, Any>
//    ): Observable<PayLaterPayModel>
//
//    @GET("cart")
//    fun cart(): Observable<CartModel>
//
//    @GET("address/courier")
//    fun shipping(
//        @Query("origin") origin: String,
//        @Query("destination") destination: String,
//    ): Observable<ShippingModel>
//
//    @GET("consumer")
//    fun address(): Observable<AddressModel>
//
//    @GET("address/provinces")
//    fun province(): Observable<LocationModel>
//
//    @GET("address/cities/{id}")
//    fun city(@Path("id") id: String): Observable<LocationModel>
//
//    @GET("address/sub_districts/{id}")
//    fun district(@Path("id") id: String): Observable<LocationModel>
//
//    @POST("consumer/delete")
//    @FormUrlEncoded
//    fun addressDelete(
//        @Field("id") id: String
//    ): Observable<DefaultModel>
//
//    @PUT("consumer")
//    @FormUrlEncoded
//    fun addressUpdate(
//        @Field("id") id: String,
//        @Field("consumer_name") consumerName: String,
//        @Field("consumer_phone") consumerPhone: String,
//        @Field("address_id") addressId: String,
//        @Field("address") address: String,
//        @Field("post_code") postCode: String
//    ): Observable<DefaultModel>
//
//    @POST("consumer")
//    @FormUrlEncoded
//    fun addressAdd(
//        @Field("consumer_name") consumerName: String,
//        @Field("consumer_phone") consumerPhone: String,
//        @Field("address_id") addressId: String,
//        @Field("address") address: String,
//        @Field("post_code") postCode: String
//    ): Observable<DefaultModel>
//
//    @GET("label")
//    fun label(): Observable<LabelModel>
//
//    @POST("label")
//    @FormUrlEncoded
//    fun labelAdd(
//        @Field("label") label: String,
//        @Field("website") website: String,
//        @Field("phone") phone: String,
//        @Field("logo") logo: String
//    ): Observable<DefaultModel>
//
//    @PUT("label")
//    @FormUrlEncoded
//    fun labelUpdate(
//        @Field("id") id: String,
//        @Field("label") label: String,
//        @Field("website") website: String,
//        @Field("phone") phone: String,
//        @Field("logo") logo: String
//    ): Observable<DefaultModel>
//
//    @POST("label/delete")
//    @FormUrlEncoded
//    fun labelDelete(
//        @Field("id") id: String
//    ): Observable<DefaultModel>
//
//    @PUT("cart")
//    fun updateCart(
//        @Body Raw: HashMap<String, Any>
//    ): Observable<DefaultModel>
//
//    @POST("cart/delete")
//    fun deleteCart(
//        @Body Raw: HashMap<String, Any>
//    ): Observable<DefaultModel>
//
//    @POST("product/wishlist")
//    @FormUrlEncoded
//    fun wishlist(
//        @Field("product_id") id: String
//    ): Observable<DefaultModel>
//
//    @GET("product/related/brand")
//    fun relatedProduct(@Query("id_produk") idProduct: String): Observable<ProductRelatedModel>
//
//    @GET("coupon")
//    fun checkPromo(
//        @Query("code") code: String,
//        @Query("type") type: String,
//        @Query("target") reg: String
//    ): Observable<CouponModel2>
//
//    @GET("product")
//    fun product(
//        @Query("page") code: Int,
//        @Query("length") length: Int,
//        @Query("search") search: String,
//        @Query("sort") sort: String,
//        @Query("campaign") campaign: String,
//        @Query("tag") tag: String,
//        @Query("markit") markit: String,
//        @Query("brand") brand: String,
//        @Query("type") type: String,
//        @Query("category") category: String,
//    ): Observable<ProductModel>
//
//    @GET("product/campaign")
//    fun campaign(
//        @Query("slug") slug: String,
//        @Query("type") type: String,
//    ): Observable<CampaignModel>
//
//    @GET("brand")
//    fun brand(
//        @Query("page") code: Int = 1,
//        @Query("length") length: Int = 1000,
//        @Query("sort") sort: String = "az"
//    ): Observable<BrandModel>
//
//    @GET("academy")
//    fun academy(
//        @Query("page") code: Int = 1,
//        @Query("length") length: Int = 1000
//    ): Observable<AcademicModel>
//
//    @GET("deposit")
//    fun deposit(): Observable<DepositSaldoModel>
//
//    @GET("deposit/history")
//    fun deposit(
//        @Query("page") code: Int = 1,
//        @Query("length") length: Int = 10
//    ): Observable<DepositModel>
//
//    @GET("deposit/history")
//    suspend fun deposit2(
//        @Query("page") code: Int = 1,
//        @Query("length") length: Int = 10
//    ): DepositModel
//
//    @GET("order")
//    fun order(
//        @Query("page") code: Int,
//        @Query("length") length: Int,
//        @Query("status") status: String,
//        @Query("search") search: String,
//        @Query("start_date") startDate: String,
//        @Query("end_date") endDate: String,
//    ): Observable<OrderModel>
//
//    @GET("invoice")
//    fun orderUnpaid(
//        @Query("page") code: Int = 1,
//        @Query("length") type: Int = 100
//    ): Observable<OrderUnpaidModel>
//
//    @GET("order/detail/{id}")
//    fun orderDetail(
//        @Path("id") id: String,
//    ): Observable<OrderDetailModel>
//
//    @GET("order/cancel-return")
//    fun returnList(): Observable<ReturnModel>
//
//    @GET("order/product-return/{id}")
//    fun orderReturnProduct(
//        @Path("id") id: String,
//    ): Observable<OrderReturnModel>
//
//    @POST("order/set-arrived")
//    @FormUrlEncoded
//    fun orderArrived(
//        @Field("order_id") id: String
//    ): Observable<DefaultModel>
//
//    @GET("validation/email")
//    fun checkEmail(
//        @Query("email") email: String
//    ): Observable<DefaultModel>
//
//    @GET("bank-account")
//    fun bankList(): Observable<BankModel>
//
//    @GET("chart/profit")
//    fun chart(): Observable<ChartModel>
//
//    @POST("register")
//    fun register(
//        @Body Raw: HashMap<String, Any>
//    ): Observable<RegisterModel>
//
//    @GET("product-digital")
//    fun productDigital(): Observable<ProductDigitalModel>
//
//    @POST("cart")
//    fun cartAdd(
//        @Body Raw: HashMap<String, Any>
//    ): Observable<DefaultModel>
//
//    @POST("member/firebase-token")
//    @FormUrlEncoded
//    fun pushToken(
//        @Field("token") token: String,
//    ): Observable<DefaultModel>
//
//    @POST("order/request-return")
//    fun orderReturnRequest(
//        @Body Raw: HashMap<String, Any>
//    ): Observable<DefaultModel>
//
//    @GET("challenge")
//    fun challenge(
//        @Query("length") length: String = "20",
//        @Query("page") page: String = "1",
//    ): Observable<ChallengeModel>
//
//    @GET("coupon")
//    fun coupon(
//        @Query("code") code: String,
//        @Query("type") type: String,
//        @Query("target") target: String,
//    ): Observable<CouponModel>
}
