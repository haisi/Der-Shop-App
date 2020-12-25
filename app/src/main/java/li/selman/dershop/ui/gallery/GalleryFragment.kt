package li.selman.dershop.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import li.selman.dershop.R
import li.selman.dershop.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentGalleryBinding>(
            inflater, R.layout.fragment_gallery, container, false
        )

        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textGallery.text = it
        })
        return binding.root
    }
}
