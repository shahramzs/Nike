package com.example.nike

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.room.Room
import com.example.nike.data.db.AppDatabase
import com.example.nike.data.repo.bannerRepository.BannerRepository
import com.example.nike.data.repo.bannerRepository.BannerRepositoryImpl
import com.example.nike.data.repo.cartRepository.CartRepository
import com.example.nike.data.repo.cartRepository.CartRepositoryImpl
import com.example.nike.data.repo.commentRepository.CommentRepository
import com.example.nike.data.repo.commentRepository.CommentRepositoryImpl
import com.example.nike.data.repo.orderRepository.OrderRepository
import com.example.nike.data.repo.orderRepository.OrderRepositoryImpl
import com.example.nike.data.repo.productRepository.ProductRepository
import com.example.nike.data.repo.productRepository.ProductRepositoryImpl
import com.example.nike.data.repo.source.bannerDataSource.BannerLocalDataSource
import com.example.nike.data.repo.source.bannerDataSource.BannerRemoteDataSource
import com.example.nike.data.repo.source.cartDataSource.CartRemoteDataSource
import com.example.nike.data.repo.source.commentDataSource.CommentRemoteDataSource
import com.example.nike.data.repo.source.orderDataSource.OrderRemoteDataSource
import com.example.nike.data.repo.source.productDataSource.ProductRemoteDataSource
import com.example.nike.data.repo.source.userDataSource.UserLocalDataSource
import com.example.nike.data.repo.source.userDataSource.UserRemoteDataSource
import com.example.nike.data.repo.userRepository.UserRepository
import com.example.nike.data.repo.userRepository.UserRepositoryImpl
import com.example.nike.feature.auth.AuthViewModel
import com.example.nike.feature.cart.CartViewModel
import com.example.nike.feature.favorites.FavoriteViewModel
import com.example.nike.feature.list.ProductListViewModel
import com.example.nike.feature.main.MainActivityViewModel
import com.example.nike.feature.main.MainViewModel
import com.example.nike.feature.main.ProductListAdapter
import com.example.nike.feature.productDetails.ProductDetailsViewModel
import com.example.nike.feature.productDetails.comments.CommentListViewModel
import com.example.nike.feature.profile.ProfileViewModel
import com.example.nike.feature.shipping.ShippingViewModel
import com.example.nike.services.http.ApiService
import com.example.nike.services.http.createApiServiceInstance
import com.example.nike.services.imageLoading.FrescoImageLoadingService
import com.example.nike.services.imageLoading.ImageLoadingService
import com.example.nike.services.imageLoading.ImageLoadingServicePicasso
import com.example.nike.services.imageLoading.PicassoImageLoadingService
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)

        val myModules = module {
            single<ApiService> { createApiServiceInstance() }
            single<ImageLoadingService>{ FrescoImageLoadingService() }
            single<ImageLoadingServicePicasso>{PicassoImageLoadingService()}
            single<SharedPreferences> { this@App.getSharedPreferences("app_settings", MODE_PRIVATE) }
            single<UserRepository> {UserRepositoryImpl(UserRemoteDataSource(get()),UserLocalDataSource(get()))  }
            single{UserLocalDataSource(get())}
            single<OrderRepository> {OrderRepositoryImpl(OrderRemoteDataSource(get()))  }
            single{ Room.databaseBuilder(this@App,AppDatabase::class.java,"da_app").build()}

            factory<ProductRepository>{ ProductRepositoryImpl(ProductRemoteDataSource(get()), get<AppDatabase>().productDao()) }
            factory<BannerRepository>{ BannerRepositoryImpl(BannerRemoteDataSource(get()),BannerLocalDataSource())}
            factory {(viewType:Int) -> ProductListAdapter(viewType,get()) }
            factory<CommentRepository>{CommentRepositoryImpl(CommentRemoteDataSource(get()))}
            factory<CartRepository>{CartRepositoryImpl(CartRemoteDataSource(get()))}

            viewModel { MainViewModel(get(), get()) }
            viewModel{(bundle:Bundle) -> ProductDetailsViewModel(bundle,get(),get())}
            viewModel{(code:String) -> CommentListViewModel(code, get())}
            viewModel{(sort:Int) -> ProductListViewModel(sort,get())}
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
            viewModel { MainActivityViewModel(get()) }
            viewModel { ShippingViewModel(get()) }
            viewModel { ProfileViewModel(get()) }
            viewModel { FavoriteViewModel(get()) }
        }

        startKoin(){
            androidContext(this@App)
             modules(myModules)
        }

        val userRepository:UserRepository = get()
        userRepository.loadToken()
    }
}