import android.app.Activity
import android.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import java.lang.ref.WeakReference
import android.view.LayoutInflater
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

/**
 *  Show alert dialog easily
 */
 
class AirDialog {

    class Button(val textOnButton: String, val onClick: () -> Unit)

    companion object {

        fun show (
                activity : Activity,
                title : String = "",
                message : String = "",
                iconDrawableId : Int? = null,
                isCancelable : Boolean = true,
                airButton1 : Button = Button("OK"){},
                airButton2 : Button? = null,
                airButton3 : Button? = null
        ) {
            val activityWeakReference = WeakReference(activity)

            val alertDialogBuilder = AlertDialog.Builder(activity)

            if (title != "") {
                alertDialogBuilder.setTitle(title)
            }

            if (message != "") {
                alertDialogBuilder.setMessage(message)
            }

            if (iconDrawableId != null) {
                alertDialogBuilder.setIcon(iconDrawableId)
            }

            alertDialogBuilder.setCancelable(isCancelable)

            alertDialogBuilder.setPositiveButton(airButton1.textOnButton) { dialogInterface, i ->
                if (activityWeakReference.get() != null) {
                    airButton1.onClick.invoke()
                }
            }

            if (airButton2 != null) {
                alertDialogBuilder.setNegativeButton(airButton2.textOnButton) { dialogInterface, i ->
                    if (activityWeakReference.get() != null) {
                        airButton2.onClick.invoke()
                    }
                }
            }

            if (airButton3 != null) {
                alertDialogBuilder.setNeutralButton(airButton3.textOnButton) { dialogInterface, i ->
                    if (activityWeakReference.get() != null) {
                        airButton3.onClick.invoke()
                    }
                }
            }

            alertDialogBuilder.show()
        }

    }

}


/**
 *  Show recycler view easily
 */

class AirRv {

    companion object {

        fun initAndGetAdapter(
                rv : RecyclerView,
                layoutManager: RecyclerView.LayoutManager,
                viewLayout: Int,
                viewHolder: (view: View) -> RecyclerView.ViewHolder,
                bindView: (viewHolder: RecyclerView.ViewHolder, position: Int) -> Unit,
                size: () -> Int
        ): RecyclerView.Adapter<RecyclerView.ViewHolder>
        {
            return initAndGetAdapter(
                    rv,
                    layoutManager,
                    {
                        0
                    },
                    {
                        viewLayout
                    },
                    fun(view: View, viewType: Int) : RecyclerView.ViewHolder {
                        return viewHolder(view)
                    },
                    fun(viewHolder: RecyclerView.ViewHolder, viewType: Int, position: Int) {
                        bindView(viewHolder, position)
                    },
                    { size.invoke() }
            )
        }

        fun initAndGetAdapter(
                rv: RecyclerView,
                layoutManager: RecyclerView.LayoutManager,
                viewType: (position: Int) -> Int,
                viewLayout: (viewType: Int) -> Int,
                viewHolder: (view: View, viewType: Int) -> RecyclerView.ViewHolder,
                bindView: (viewHolder: RecyclerView.ViewHolder, viewType: Int, position: Int) -> Unit,
                size: () -> Int

        ): RecyclerView.Adapter<RecyclerView.ViewHolder>
        {
            val rvAdapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

                override fun getItemViewType(position: Int): Int {
                    return viewType(position)
                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                    val view = LayoutInflater.from(rv.context).inflate(viewLayout(viewType), parent, false)
                    return viewHolder(view, viewType)
                }

                override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                    bindView(holder, viewType(position), position)
                }

                override fun getItemCount(): Int {
                    return size()
                }

            }

            rv.layoutManager = layoutManager
            rv.adapter = rvAdapter
            return rvAdapter
        }

    }

}


/**
 *  Do background or long running tasks easily
 */

class AirBg {

    companion object {

        fun perform(activity: Activity, bgTask: () -> Boolean, uiTask: (isSuccess: Boolean) -> Unit) {
            val activityWeakReference  = WeakReference<Activity>(activity)

            async (UI) {
                val data: Deferred<Boolean> = bg {
                    bgTask.invoke()
                }

                fun checker(isSuccess: Boolean, uiTask: (isSuccess: Boolean) -> Unit) {
                    if (activityWeakReference.get() == null) {
                        return
                    }
                    uiTask(isSuccess)
                }

                checker(data.await(), uiTask)
            }
        }

    }

}
