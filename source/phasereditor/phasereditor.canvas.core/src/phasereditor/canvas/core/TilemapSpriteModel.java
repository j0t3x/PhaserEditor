// The MIT License (MIT)
//
// Copyright (c) 2015, 2018 Arian Fornaris
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
package phasereditor.canvas.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.json.JSONArray;
import org.json.JSONObject;

import phasereditor.assetpack.core.AssetPackCore;
import phasereditor.assetpack.core.IAssetKey;
import phasereditor.assetpack.core.ImageAssetModel;
import phasereditor.assetpack.core.TilemapAssetModel;

/**
 * 
 * @author arian
 *
 */
public class TilemapSpriteModel extends AssetSpriteModel<TilemapAssetModel> {

	public static final String TYPE_NAME = "tilemap";

	private int _tileWidth;
	private int _tileHeight;
	private ImageAssetModel _tilesetImage;
	private boolean _resizeWorld;
	private List<Integer> _collisionIndexes;

	public TilemapSpriteModel(GroupModel parent, TilemapAssetModel assetKey) {
		super(parent, assetKey, TYPE_NAME);
		_tileWidth = 32;
		_tileHeight = 32;
		_resizeWorld = true;
		_collisionIndexes = Collections.emptyList();
	}

	public TilemapSpriteModel(GroupModel parent, JSONObject obj) {
		super(parent, TYPE_NAME, obj);
	}

	@Override
	protected void writeInfo(JSONObject jsonInfo, boolean saving) {
		super.writeInfo(jsonInfo, saving);

		jsonInfo.put("tileWidth", _tileWidth, 32);
		jsonInfo.put("tileHeight", _tileHeight, 32);
		jsonInfo.put("tilesetImage", _tilesetImage == null ? null : AssetPackCore.getAssetJSONReference(_tilesetImage));
		jsonInfo.put("resizeWorld", _resizeWorld, true);

		if (!_collisionIndexes.isEmpty()) {
			JSONArray list = new JSONArray();
			for (Integer i : _collisionIndexes) {
				list.put(i.intValue());
			}
			jsonInfo.put("collision.indexes", list);
		}
	}

	@SuppressWarnings("boxing")
	@Override
	public void readInfo(JSONObject jsonInfo) {
		super.readInfo(jsonInfo);

		_tileWidth = jsonInfo.optInt("tileWidth", 32);
		_tileHeight = jsonInfo.optInt("tileHeight", 32);

		{
			JSONObject jsonRef = jsonInfo.optJSONObject("tilesetImage");

			_tilesetImage = null;

			if (jsonRef != null) {
				IProject project = getWorld().getProject();
				Object asset = AssetPackCore.findAssetElement(project, jsonRef);
				if (asset != null && asset instanceof ImageAssetModel) {
					_tilesetImage = (ImageAssetModel) asset;
				}
			}
		}

		_resizeWorld = jsonInfo.optBoolean("resizeWorld", true);

		{
			_collisionIndexes = new ArrayList<>();
			JSONArray list = jsonInfo.optJSONArray("collision.indexes");
			if (list != null) {
				for (int i = 0; i < list.length(); i++) {
					_collisionIndexes.add(list.getInt(i));
				}
			}
		}
	}

	@Override
	public void build() {
		super.build();

		IAssetKey asset = buildAnyAssetKey(_tilesetImage);

		_tilesetImage = asset instanceof ImageAssetModel ? (ImageAssetModel) asset : null;
	}

	public int getTileWidth() {
		return _tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		_tileWidth = tileWidth;
	}

	public int getTileHeight() {
		return _tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		_tileHeight = tileHeight;
	}

	public ImageAssetModel getTilesetImage() {
		return _tilesetImage;
	}

	public void setTilesetImage(ImageAssetModel tilesetImage) {
		_tilesetImage = tilesetImage;
	}

	public boolean isResizeWorld() {
		return _resizeWorld;
	}

	public void setResizeWorld(boolean layerResizeWorld) {
		_resizeWorld = layerResizeWorld;
	}

	public List<Integer> getCollisionIndexes() {
		return _collisionIndexes;
	}

	public void setCollisionIndexes(List<Integer> collisionIndexes) {
		_collisionIndexes = collisionIndexes;
	}

}
