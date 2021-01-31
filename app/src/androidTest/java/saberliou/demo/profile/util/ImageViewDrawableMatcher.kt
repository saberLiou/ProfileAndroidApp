package saberliou.demo.profile.util

import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description

object ImageViewDrawableMatcher {
    fun withDrawable(@DrawableRes resourceId: Int): BoundedMatcher<View, ImageView> =
        object : BoundedMatcher<View, ImageView>(ImageView::class.java) {
            private var resourceName: String? = null

            override fun describeTo(description: Description) {
                description.appendText("Drawable $resourceId")
                if (resourceName != null) {
                    description.appendText(": $resourceName")
                }
            }

            override fun matchesSafely(imageView: ImageView): Boolean {
                val context = imageView.context
                resourceName = context.resources.getResourceEntryName(resourceId)

                val actualBitmap = (imageView.drawable as BitmapDrawable).bitmap
                val expectedBitmap =
                    (ContextCompat.getDrawable(context, resourceId) as BitmapDrawable).bitmap
                return actualBitmap.sameAs(expectedBitmap)
            }
        }
}
