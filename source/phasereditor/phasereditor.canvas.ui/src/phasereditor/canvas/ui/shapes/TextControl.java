// The MIT License (MIT)
//
// Copyright (c) 2015, 2017 Arian Fornaris
//
// Permission is hereby granted, free of charge, to any person obtaining a
// copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to permit
// persons to whom the Software is furnished to do so, subject to the
// following conditions: The above copyright notice and this permission
// notice shall be included in all copies or substantial portions of the
// Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
// OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
// NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
// DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
// OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
// USE OR OTHER DEALINGS IN THE SOFTWARE.
package phasereditor.canvas.ui.shapes;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import phasereditor.canvas.core.TextModel;
import phasereditor.canvas.core.TextStyle;
import phasereditor.canvas.ui.editors.ObjectCanvas;
import phasereditor.canvas.ui.editors.grid.PGridModel;
import phasereditor.canvas.ui.editors.grid.PGridSection;
import phasereditor.canvas.ui.editors.grid.PGridStringProperty;
import phasereditor.canvas.ui.editors.grid.PGridTextStyleProperty;

/**
 * 
 * @author arian
 */
public class TextControl extends BaseSpriteControl<TextModel> {

	public TextControl(ObjectCanvas canvas, TextModel model) {
		super(canvas, model);
	}

	@Override
	protected IObjectNode createNode() {
		return new TextNode(this);
	}

	@Override
	public double getTextureWidth() {
		return new Text(getModel().getText()).getBoundsInLocal().getWidth();
	}

	@Override
	public double getTextureHeight() {
		return new Text(getModel().getText()).getBoundsInLocal().getHeight();
	}

	@Override
	public void updateFromModel() {
		TextModel model = getModel();
		TextNode node = getNode();

		node.setText(model.getText());

		TextStyle style = model.getStyle();
		Font font = Font.font(style.getFont(), FontWeight.NORMAL, style.getFontStyle(), style.getFontSize());
		node.setFont(font);

		super.updateFromModel();
	}

	@Override
	public TextNode getNode() {
		return (TextNode) super.getNode();
	}

	@Override
	protected void initPGridModel(PGridModel propModel) {
		super.initPGridModel(propModel);

		PGridSection section = new PGridSection("Text");

		section.add(new PGridStringProperty(getId(), "text", help("Phaser.Text.text"), "Write the text.") {

			@Override
			public boolean isModified() {
				return getModel().getText().length() > 0;
			}

			@Override
			public void setValue(String value, boolean notify) {
				getModel().setText(value);
				if (notify) {
					updateFromPropertyChange();
				}
			}

			@Override
			public String getValue() {
				return getModel().getText();
			}
		});

		section.add(new PGridTextStyleProperty(getModel()) {

			@Override
			public void setValue(TextStyle value, boolean notify) {
				getModel().setStyle(value);
				if (notify) {
					updateFromPropertyChange();
				}
			}

			@Override
			public TextStyle getValue() {
				return getModel().getStyle();
			}

			@Override
			public boolean isModified() {
				return true;
			}

		});

		propModel.getSections().add(section);

		// will never be supported by text, this should be moved to asset
		// sprites.
		getAnimationsProperty().getSection().remove(getAnimationsProperty());
		// not supported on Phaser v2
		getTintProperty().getSection().remove(getTintProperty());
	}

}
