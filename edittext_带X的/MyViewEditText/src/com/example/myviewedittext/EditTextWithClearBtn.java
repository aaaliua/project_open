package com.example.myviewedittext;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EditTextWithClearBtn extends LinearLayout {

	private Resources res;

	private TextView tv;
	private EditText et;
	private ImageView iv;

	private TextWatcher mWatcher;

	private CharSequence hint;

	/**
	 * 返回左侧文字
	 * 
	 * @return
	 */
	public CharSequence getLeftText() {
		return tv.getText();
	}

	/**
	 * 设置左侧文字
	 */
	public void setLeftText(CharSequence mText) {
		this.tv.setText(mText);
	}

	/**
	 * 返回输入框内文字
	 * 
	 * @return
	 */
	public CharSequence getText() {
		return et.getText();
	}

	/**
	 * 设置输入框内文字
	 */
	public void setText(CharSequence mText) {
		this.et.setText(mText);
	}

	/**
	 * 返回输入框内hint文字
	 * 
	 * @return
	 */
	public CharSequence getHint() {
		return hint;
	}

	/**
	 * 设置输入框内hint文字
	 */
	public void setHint(CharSequence mText) {
		hint = mText;
		this.et.setHint(hint);
	}

	public EditTextWithClearBtn(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		res = getResources();
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.EditTextWithClearBtn);
		CharSequence textLeft = ta
				.getText(R.styleable.EditTextWithClearBtn_textLeft);
		CharSequence text = ta.getText(R.styleable.EditTextWithClearBtn_text);
		hint = ta.getText(R.styleable.EditTextWithClearBtn_hint);
		float textSize = ta.getDimension(
				R.styleable.EditTextWithClearBtn_text_size, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_SP, 5, context
								.getResources().getDisplayMetrics()));

		// 左侧的文字
		tv = new TextView(context);
		tv.setPadding(10, 0, 0, 0);
		tv.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		tv.setText(textLeft);
		tv.setTextColor(res.getColor(R.color.txt_gray));
		tv.setTextSize(textSize);// sp
		tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		addView(tv);

		// 中间的输入框
		et = new EditText(context);
		et.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1));
		et.setTextSize(textSize);// sp
		et.setBackgroundColor(res.getColor(R.color.transparent));
		et.setTextColor(res.getColor(R.color.txt_gray));
		et.setSingleLine(true);
		et.setText(text);
		et.setHint(hint);
		et.setHintTextColor(res.getColor(R.color.txt_light_gray));
		Log.i("INFO", hint.toString());
		et.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					et.setHint("");
				} else {
					et.setHint(hint);
				}
				// 获取焦点时才显示clear按钮
				String value = et.getText().toString();
				if (hasFocus && value != null && value.length() > 0) {
					iv.setVisibility(View.VISIBLE);

				} else {
					iv.setVisibility(View.INVISIBLE);
				}
			}
		});
		et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s != null && s.toString().length() > 0) {
					iv.setVisibility(View.VISIBLE);
				} else {
					iv.setVisibility(View.INVISIBLE);
				}
				if (mWatcher != null) {
					mWatcher.onTextChanged(s, start, before, count);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				if (mWatcher != null) {
					mWatcher.beforeTextChanged(s, start, count, after);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (mWatcher != null) {
					mWatcher.afterTextChanged(s);
				}
			}
		});

		addView(et);

		// 右边的删除按钮
		iv = new ImageView(context);
		iv.setPadding(0, 15, 25, 15);
		iv.setImageResource(R.drawable.icon_delete);
		iv.setVisibility(View.INVISIBLE);
		LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		iv.setLayoutParams(layoutParams);
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (iv.getVisibility() == View.VISIBLE) {
					et.setText("");
				}
			}
		});
		addView(iv);

		ta.recycle();
	}

	public EditTextWithClearBtn(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public EditTextWithClearBtn(Context context) {
		this(context, null);
	}

	public void setFilters(InputFilter[] filters) {
		et.setFilters(filters);
	}

	public void addTextChangedListener(TextWatcher watcher) {
		mWatcher = watcher;
	}

}
