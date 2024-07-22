package com.ramsoft.mydog.ui.favouritedog.alldogs



import android.content.Context
import com.ramsoft.mydog.data.api.DogApiService
import com.ramsoft.mydog.data.repository.DogRepository
import com.ramsoft.mydog.ui.alldogs.intent.AllDogsIntent
import com.ramsoft.mydog.ui.alldogs.model.AllDogsBreedsResponse
import com.ramsoft.mydog.ui.alldogs.viewmodel.AllDogsViewModel
import com.ramsoft.mydog.ui.alldogs.viewstate.AllDogsState
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
class AllDogsViewModelTest {
    @get:Rule
    val rule = CoroutineTestRule()
    private lateinit var viewModel: AllDogsViewModel

    @Mock
    private lateinit var mockApiService: DogApiService

    private lateinit var repository: DogRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = DogRepository(mock(Context::class.java), mockApiService)
        viewModel = AllDogsViewModel(repository)
    }

    @Test
    fun `fetchAllBreeds success`() = runTest {
        var state = viewModel.state.first()
        assert(state is AllDogsState.LoadingState)
        val message =  HashMap<String, List<String>>()
        message["affenpinscher"] = listOf()
        message["african"] = listOf()
        message["australian"] = listOf("kelpie", "shepherd")
        val response = AllDogsBreedsResponse(message,"success")
        viewModel.sendIntent(AllDogsIntent.FetchAllBreeds)
        `when`(mockApiService.getAllBreedsList()).thenReturn(Response.success(response).body())
        state = AllDogsState.FetchAllDogsBreeds(response.message)
        assert((state.allBreeds) == response.message)
    }

    @Test
    fun `fetchAllBreeds error`() = runTest {
        var state = viewModel.state.first()
        assert(state is AllDogsState.LoadingState)
        val message =  HashMap<String, List<String>>()

        val response = AllDogsBreedsResponse(message,"Network Error")
        viewModel.sendIntent(AllDogsIntent.FetchAllBreeds)
        `when`(mockApiService.getAllBreedsList()).then {
            Throwable("Network error")
        }
        state = AllDogsState.FetchAllDogsBreeds(response.message)
        assert((state.allBreeds) == response.message)

    }
}