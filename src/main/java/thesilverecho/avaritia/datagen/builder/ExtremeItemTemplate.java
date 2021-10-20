package thesilverecho.avaritia.datagen.builder;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ExtremeItemTemplate extends ItemModelBuilder
{

	private boolean pulse;
	private ResourceLocation backgroundTexture;
	private ResourceLocation parent;
	private int colour = -1, size = -1;

	public ExtremeItemTemplate(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper)
	{
		super(outputLocation, existingFileHelper);
	}

	public ExtremeItemTemplate pulse()
	{
		this.pulse = true;
		return this;
	}

	public ExtremeItemTemplate setBackgroundTexture(ResourceLocation backgroundTexture)
	{
		this.backgroundTexture = backgroundTexture;
		return this;
	}

	public ExtremeItemTemplate setColour(int colour)
	{
		this.colour = colour;
		return this;
	}

	public ExtremeItemTemplate setSize(int size)
	{
		this.size = size;
		return this;
	}

	public ExtremeItemTemplate setParent(ResourceLocation parent)
	{
		this.parent = parent;
		return this;
	}

	@Override
	public JsonObject toJson()
	{
		JsonObject json = super.toJson();
		json.addProperty("loader", "avaritia:extreme");
		json.addProperty("parent", this.parent.toString());
		if (this.pulse)
		{
			json.addProperty("pulse", true);
		}
		if (this.backgroundTexture != null)
		{
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("texture", this.backgroundTexture.toString());

			if (this.size != -1)
				jsonObject.addProperty("size", this.size);
			if (this.colour != -1)
				jsonObject.addProperty("colour", this.colour);

			json.add("background", jsonObject);
		}
		return json;
	}


}
