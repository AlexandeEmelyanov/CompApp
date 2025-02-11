import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.compapp.R
import com.example.compapp.Table
import com.example.compapp.DbHelper

class TableAdapter(private val dbHelper: DbHelper, private val tables: MutableList<Table>) : RecyclerView.Adapter<TableAdapter.ViewHolder>() {



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tableButton: Button = itemView.findViewById(R.id.tableButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.table_item, parent, false) // table_item.xml - разметка для одной кнопки
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val table = tables[position]
        holder.tableButton.text = "Стол ${table.id + 1}"
        holder.tableButton.setOnClickListener {
            table.status = 1 - table.status
            dbHelper.updateTableStatus(table.id, table.status)
            notifyItemChanged(position)
        }
        holder.tableButton.setBackgroundColor(if (table.status == 0) Color.BLUE else Color.RED)
    }



    override fun getItemCount(): Int = tables.size
}
