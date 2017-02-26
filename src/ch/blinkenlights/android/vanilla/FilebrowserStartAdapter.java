/*
 * Copyright (C) 2013-2016 Adrian Ulrich <adrian@blinkenlights.ch>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>. 
 */

package ch.blinkenlights.android.vanilla;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class FilebrowserStartAdapter
	extends ArrayAdapter<String>
{
	
	private final LayoutInflater mInflater;

	public FilebrowserStartAdapter(Context context, int resource) {
		super(context, resource);
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		FileSystemRowView row;
//		TextView row;
		ViewHolder holder;

		if (convertView == null) {

			row = (FileSystemRowView)mInflater.inflate(R.layout.filesystemrow, parent, false);
			row.setupLayout(DraggableRow.LAYOUT_LISTVIEW);

//			row.getCoverView().setImageResource(R.drawable.folder);
			holder = new ViewHolder();
			row.setTag(holder);
/*
			row = new TextView(getContext());
*/
		} else {
			row = (FileSystemRowView)convertView;
//			row = (TextView)convertView;
			holder = (ViewHolder)row.getTag();
		}

		String label = getItem(pos);
		holder.id = pos;
//		row.setText(label);
		row.getTextView().setText(label);
		return row;
	}

}
