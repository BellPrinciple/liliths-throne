package com.lilithsthrone.game.character.body.abstractTypes;

import java.util.List;

import com.lilithsthrone.game.character.body.types.FootType;
import com.lilithsthrone.game.character.body.valueEnums.FootStructure;
import com.lilithsthrone.game.character.body.valueEnums.LegConfiguration;

/**
 * @since 0.3.1
 * @version 0.3.1
 * @author Innoxia
 */
public abstract class AbstractFootType implements FootType {
	
	private String typeName;
	
	private String footName;
	private String footNamePlural;
	
	private List<String> footDescriptorsMasculine;
	private List<String> footDescriptorsFeminine;
	
	private String toeSingularName;
	private String toePluralName;
	private List<String> toesDescriptorsMasculine;
	private List<String> toesDescriptorsFeminine;
	
	private String footjobName;
	
	private String footBodyDescription;
	
	private List<FootStructure> permittedFootStructures;

	/**
	 * @param typeName The name/singular descriptor of this foot type.
	 * @param footName The singular name of the foot, such as 'foot', 'paw', 'hoof', etc.
	 * @param footNamePlural The plural name of the foot, such as 'feet', 'paws', 'hoofs', etc.
	 * @param footDescriptorsMasculine The descriptors that can be used to describe a masculine form of this foot type.
	 * @param footDescriptorsFeminine The descriptors that can be used to describe a feminine form of this foot type
	 * @param toeSingularName The singular name of a toe attached to this foot type, such as 'toe', 'claw', etc.
	 * @param toePluralName The plural name of toes attached to this foot type, such as 'toes', 'claws', etc.
	 * @param toesDescriptorsMasculine The descriptors that can be used to describe a masculine form of the related toes.
	 * @param toesDescriptorsFeminine The descriptors that can be used to describe a feminine form of the related toes.
	 * @param footjobName The name of the action when using this foot type to give a footjob.
	 * @param footBodyDescription A sentence or two to describe this foot type, as seen in the character view screen. It should follow the same format as all of the other entries in the FootType class.
	 * @param permittedFootStructures The permitted types of FootStructure this FootType can have.
	 */
	public AbstractFootType(String typeName,
			String footName,
			String footNamePlural,
			List<String> footDescriptorsMasculine,
			List<String> footDescriptorsFeminine,
			String toeSingularName,
			String toePluralName,
			List<String> toesDescriptorsMasculine,
			List<String> toesDescriptorsFeminine,
			String footjobName,
			String footBodyDescription,
			List<FootStructure> permittedFootStructures) {
		
		this.typeName = typeName;
		
		this.footName = footName;
		this.footNamePlural = footNamePlural;
		this.footDescriptorsMasculine = footDescriptorsMasculine;
		this.footDescriptorsFeminine = footDescriptorsFeminine;
		
		this.toeSingularName = toeSingularName;
		this.toePluralName = toePluralName;
		this.toesDescriptorsMasculine = toesDescriptorsMasculine;
		this.toesDescriptorsFeminine = toesDescriptorsFeminine;
		
		this.footjobName = footjobName;
		
		this.footBodyDescription = footBodyDescription;
		
		this.permittedFootStructures = permittedFootStructures;
	}

	@Override
	public String getTypeName() {
		return typeName;
	}

	@Override
	public String getFootName() {
		return footName;
	}

	@Override
	public String getFootNamePlural() {
		return footNamePlural;
	}

	@Override
	public List<String> getFootDescriptorsMasculine() {
		return footDescriptorsMasculine;
	}

	@Override
	public List<String> getFootDescriptorsFeminine() {
		return footDescriptorsFeminine;
	}

	@Override
	public String getToeSingularName() {
		return toeSingularName;
	}

	@Override
	public String getToePluralName() {
		return toePluralName;
	}

	@Override
	public List<String> getToeDescriptorsMasculine() {
		return toesDescriptorsMasculine;
	}

	@Override
	public List<String> getToeDescriptorsFeminine() {
		return toesDescriptorsFeminine;
	}

	@Override
	public String getFootjobName() {
		return footjobName;
	}

	@Override
	public String getFootBodyDescription() {
		return footBodyDescription;
	}

	@Override
	public List<FootStructure> getPermittedFootStructures(LegConfiguration legConfiguration) {
		if(legConfiguration==null || legConfiguration.getPermittedFootStructuresOverride().isEmpty()) {
			return permittedFootStructures;
		}
		return legConfiguration.getPermittedFootStructuresOverride();
	}
}
