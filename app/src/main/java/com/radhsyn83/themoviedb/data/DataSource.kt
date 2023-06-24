package com.radhsyn83.themoviedb.data

import androidx.lifecycle.LiveData
import com.radhsyn83.themoviedb.data.models.DetailModel
import com.radhsyn83.themoviedb.data.models.GenresModel
import com.radhsyn83.themoviedb.data.models.MoviesModel
import com.radhsyn83.themoviedb.data.models.ReviewsModel
import com.radhsyn83.themoviedb.data.models.TrailersModel
import com.radhsyn83.themoviedb.vo.Resource

interface DataSource {
     fun genres() : LiveData<Resource<GenresModel>>
     fun movies(id: Int, page: Int) : LiveData<Resource<MoviesModel>>
     fun trailers(id: Int) : LiveData<Resource<TrailersModel>>
     fun reviews(id: Int) : LiveData<Resource<ReviewsModel>>
     fun detail(id: Int) : LiveData<Resource<DetailModel>>

//    fun register(data: HashMap<String, Any>) : LiveData<Resource<RegisterModel.Data>>
//    fun checkEmail(email: String) : LiveData<Resource<DefaultModel>>
//    fun productDigital() : LiveData<Resource<ArrayList<ProductDigitalModel.Data>>>
//    fun banner() : LiveData<Resource<List<BannerEntity>>>
//    fun orderDetail(id: String) : LiveData<Resource<OrderDetailModel.Data>>
//    fun orderArrived(id: String) : LiveData<Resource<DefaultModel>>
//    fun returnList() : LiveData<Resource<ArrayList<ReturnModel.Data>>>
//    fun orderReturnProduct(id: String) : LiveData<Resource<ArrayList<OrderReturnModel.Data>>>
//    fun orderReturnRequest(data: HashMap<String, Any>) : LiveData<Resource<DefaultModel>>
//    fun challenge() : LiveData<Resource<ArrayList<ChallengeModel.Data.Result>>>
//    fun coupon(code: String, type: String, target: String) : LiveData<Resource<CouponModel>>
//    suspend fun deposit(page: Int) : DepositModel
}