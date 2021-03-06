package me.devsaki.hentoid.listener;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

import me.devsaki.hentoid.database.domains.Content;
import me.devsaki.hentoid.util.FileHelper;
import me.devsaki.hentoid.util.LogHelper;

/**
 * Created by avluis on 05/07/2016.
 * Item OnClick and OnLongClick Listener with support for item selection
 */
public class ItemClickListener implements OnClickListener, OnLongClickListener {
    private static final String TAG = LogHelper.makeLogTag(ItemClickListener.class);

    private final Context context;
    private final Content content;
    private final ItemSelectListener listener;
    private final int position;
    private int selectedItemCount;
    private boolean selected;

    protected ItemClickListener(Context cxt, Content content, int pos, ItemSelectListener listener) {
        this.context = cxt;
        this.content = content;
        this.position = pos;
        this.selectedItemCount = 0;
        this.listener = listener;
    }

    protected void setSelected(boolean selected, int selectedItemCount) {
        this.selected = selected;
        this.selectedItemCount = selectedItemCount;
    }

    private void updateSelector() {
        if (selected) {
            listener.onItemSelected(selectedItemCount);
        } else {
            listener.onItemClear(selectedItemCount);
        }
    }

    @Override
    public void onClick(View v) {
        if (!selected) {
            FileHelper.openContent(context, content);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        updateSelector();

        LogHelper.d(TAG, "Position: " + position + ": " + content.getTitle() +
                " has been" + (selected ? " selected." : " unselected."));

        return true;
    }

    public interface ItemSelectListener {
        void onItemSelected(int selectedCount);

        void onItemClear(int itemCount);
    }
}
