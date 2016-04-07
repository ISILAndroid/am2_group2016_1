package com.isil.fragments.view;

import com.isil.fragments.model.BookEntity;

/**
 * Created by em on 30/03/16.
 */
public interface KindleListener {

    void filterSelected(int position,Object object);
    void gotoBookDetail(BookEntity bookEntity);

}
