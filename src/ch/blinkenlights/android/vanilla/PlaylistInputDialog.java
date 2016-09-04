/*
 * Copyright (C) 2016 Adrian Ulrich <adrian@blinkenlights.ch>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package ch.blinkenlights.android.vanilla;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;

public class PlaylistInputDialog extends DialogFragment
	implements DialogInterface.OnClickListener, TextWatcher
{

	public interface Callback {
		void onSuccess(String input);
	}

	/**
	 * A editText instance
	 */
	private EditText mEditText;
	private Callback mCallback;
	private String mInitialText;
	private AlertDialog mDialog;
	private int mActionRes;

	PlaylistInputDialog(Callback callback, String initialText, int actionRes) {
		mCallback = callback;
		mInitialText = initialText;
		mActionRes = actionRes;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mEditText = new EditText(getActivity());
		mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
		mEditText.addTextChangedListener(this);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.choose_playlist_name)
			.setView(mEditText)
			.setPositiveButton(mActionRes, this)
			.setNegativeButton(android.R.string.cancel, this);
		mDialog = builder.create();
		return mDialog;
	}

	public void onStart() {
		super.onStart();
		mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
		mEditText.setText(mInitialText);
		mEditText.requestFocus();
	}

	public void afterTextChanged(Editable s) {
		// noop
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// noop
	}

	public void onTextChanged(CharSequence text, int start, int before, int count) {
		String string = text.toString();
		if (string.equals(mInitialText)) {
			mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
		} else {
			mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
			ContentResolver resolver = getContext().getContentResolver();
			int res = Playlist.getPlaylist(resolver, string) == -1 ? mActionRes : R.string.overwrite;
			mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(res);
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
			case DialogInterface.BUTTON_NEGATIVE:
				// noop
				break;
			case DialogInterface.BUTTON_POSITIVE:
				mCallback.onSuccess(mEditText.getText().toString());
				break;
			default:
				break;
		}
		dialog.dismiss();
	}
}
