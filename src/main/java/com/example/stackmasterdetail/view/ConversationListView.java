/*
 * Copyright 2013 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.stackmasterdetail.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.stackmasterdetail.Paths;
import com.example.stackmasterdetail.model.Conversation;
import com.example.stackmasterdetail.util.BackstackService;
import com.example.stackmasterdetail.util.Utils;

import java.util.List;

import javax.inject.Inject;

public class ConversationListView
        extends ListView
        implements IsMasterView {
    @Inject
    List<Conversation> conversations;

    public ConversationListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Utils.getComponent(context).inject(this);

        Adapter adapter = new Adapter(getContext(), conversations);

        setAdapter(adapter);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BackstackService.get(getContext()).goTo(Paths.Conversation.create(position));
            }
        });
    }

    @Override
    public void updateSelection(Paths.MasterDetailPath newPath) {
        Paths.ConversationPath path = (Paths.ConversationPath) newPath;
        setItemChecked(path.conversationIndex(), true);
        invalidate();
    }

    private static class Adapter
            extends ArrayAdapter<Conversation> {
        public Adapter(Context context, List<Conversation> objects) {
            super(context, android.R.layout.simple_list_item_activated_1, objects);
        }
    }
}
