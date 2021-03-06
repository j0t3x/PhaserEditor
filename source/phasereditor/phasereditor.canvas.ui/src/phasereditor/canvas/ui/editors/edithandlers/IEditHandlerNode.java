// The MIT License (MIT)
//
// Copyright (c) 2015, 2016 Arian Fornaris
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
package phasereditor.canvas.ui.editors.edithandlers;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import phasereditor.canvas.core.BaseObjectModel;
import phasereditor.canvas.core.BaseSpriteModel;
import phasereditor.canvas.ui.editors.behaviors.HandlerBehavior.TransformationCoords;
import phasereditor.canvas.ui.shapes.BaseObjectControl;
import phasereditor.canvas.ui.shapes.IObjectNode;

/**
 * @author arian
 *
 */
@SuppressWarnings("unused")
public interface IEditHandlerNode {

	IObjectNode getObject();

	void updateHandler();
	
	default Point2D computeObjectCenter_global() {
		BaseObjectModel model = getObject().getModel();
		BaseObjectControl<?> control = getObject().getControl();
		Node node = getObject().getNode();
		
		
		double centerX = model.getPivotX();
		double centerY = model.getPivotY();

		if (model instanceof BaseSpriteModel) {
			BaseSpriteModel spriteModel = (BaseSpriteModel) model;
			double x = spriteModel.getAnchorX() * control.getTextureWidth();
			double y = spriteModel.getAnchorY() * control.getTextureHeight();
			centerX = centerX + x;
			centerY = centerY + y;
		}

		
		Point2D p = node.localToScene(centerX, centerY);
		centerX = p.getX();
		centerY = p.getY();
		
		return new Point2D(centerX, centerY);
	}

	default boolean isLocalCoords() {
		return getTransformationCoords() == TransformationCoords.LOCAL;
	}
	
	default boolean isGlobalCoords() {
		return getTransformationCoords() == TransformationCoords.GLOBAL;
	}

	default TransformationCoords getTransformationCoords() {
		return getObject().getControl().getCanvas().getHandlerBehavior().getTransformationCoords();
	}
	
	default void handleMouseMoved(MouseEvent e) {
		// nothing
	}

	default void handleMousePressed(MouseEvent e) {
		// nothing
	}

	default void handleMouseDragged(MouseEvent e) {
		// nothing
	}

	default void handleMouseReleased(MouseEvent e) {
		// nothing
	}

	default void handleMouseExited(MouseEvent e) {
		// nothing
	}

	default void handleSceneStart(double x, double y, MouseEvent e) {
		// nothing
	}

	default void handleLocalStart(double x, double y, MouseEvent e) {
		// nothing
	}

	default void handleSceneDrag(double dx, double dy, MouseEvent e) {
		// nothing
	}

	default void handleLocalDrag(double dx, double dy, MouseEvent e) {
		// nothing
	}

	default void handleDone() {
		// nothing
	}

	/**
	 * Transform an object local position into an scene position.
	 */
	default Point2D objectToScene(double x, double y) {
		Point2D p = getObject().getNode().localToScene(new Point2D(x, y));
		return p;
	}

	default Point2D sceneToObject(double x, double y) {
		Point2D p = getObject().getNode().sceneToLocal(new Point2D(x, y));
		return p;
	}

	boolean isValid();
}