package com.ramsoft.mydog.ui.favouritedog

import android.content.Context
import com.ramsoft.mydog.data.api.DogApiService
import com.ramsoft.mydog.data.repository.DogRepository
import com.ramsoft.mydog.ui.favouritedog.intent.FavouriteDogIntent
import com.ramsoft.mydog.ui.favouritedog.model.DogImageApiResponse
import com.ramsoft.mydog.ui.favouritedog.viewmodel.FavouriteDogViewModel
import com.ramsoft.mydog.ui.favouritedog.viewstate.FavouriteDogState
import com.ramsoft.mydog.utils.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

/**
 * @author Priyesh Bhargava
 */
@ExperimentalCoroutinesApi
class FavouriteDogViewModelTest {
    @get:Rule
    val rule = CoroutineTestRule()
    private lateinit var viewModel: FavouriteDogViewModel

    @Mock
    private lateinit var mockApiService: DogApiService

    private lateinit var repository: DogRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = DogRepository(mock(Context::class.java), mockApiService)
        viewModel = FavouriteDogViewModel(repository)
    }

    @Test
    fun `fetchDogImage success`() = runTest {
        var state = viewModel.state.first()

        assert(state is FavouriteDogState.LoadingState)
        val response = DogImageApiResponse(
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
            "success"
        )
        viewModel.sendIntent(FavouriteDogIntent.LoadingIntent)
        `when`(mockApiService.getRandomDogImage()).thenReturn(Response.success(response).body())
        state = FavouriteDogState.DogSuccessState(response.message)
        assert((state).url == response.message)
    }

    @Test
    fun `fetchDogImage error`() = runTest {
        var state = viewModel.state.first()
        assert(state is FavouriteDogState.LoadingState)
        val response = DogImageApiResponse(
            "",
            "Network Error"
        )
        viewModel.sendIntent(FavouriteDogIntent.LoadingIntent)
        `when`(mockApiService.getRandomDogImage()).then {
            Throwable("Network error")
        }
        state = FavouriteDogState.DogSuccessState(response.message)
        assert((state).url == response.message)
    }
}