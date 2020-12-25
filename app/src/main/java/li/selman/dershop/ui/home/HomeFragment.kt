package li.selman.dershop.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import li.selman.dershop.R
import li.selman.dershop.business.product.Product
import li.selman.dershop.databinding.FragmentHomeBinding
import li.selman.dershop.ui.home.grid.HomeProductAdapter
import li.selman.dershop.ui.util.BindableViewHolder

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ListAdapter<Product, BindableViewHolder<Product>>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home, container, false
        )

        viewManager = GridLayoutManager(this.context, 2)
        viewAdapter =
            HomeProductAdapter(HomeProductAdapter.ProductListener { productId ->
                homeViewModel.onProductClicked(productId)
            })

        binding.myRecyclerView.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = viewAdapter

        }

        homeViewModel.navigateToProductDetails.observe(this.viewLifecycleOwner, Observer { productId ->
            productId?.let {
                Toast.makeText(this.context, "$productId", Toast.LENGTH_SHORT).show()
                // TODO
//                this.findNavController().navigate(
//
//                )

                homeViewModel.onProductDetailsNavigated()
            }
        })

        homeViewModel.products.observe(viewLifecycleOwner, Observer { products ->
            products?.let {
                viewAdapter.submitList(products)
            }
        })

        binding.lifecycleOwner = this

        return binding.root
    }
}
