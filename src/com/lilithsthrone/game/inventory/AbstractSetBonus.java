package com.lilithsthrone.game.inventory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.lilithsthrone.main.Main;
import org.w3c.dom.Document;

import com.lilithsthrone.controller.xmlParsing.Element;
import com.lilithsthrone.game.character.effects.StatusEffect;

/**
 * @since 0.3.8.2
 * @version 0.3.8.2
 * @author Innoxia
 */
public abstract class AbstractSetBonus implements SetBonus {

	String id;
	private boolean mod;
	private String name;
	private int numberRequiredForCompleteSet;
	private List<InventorySlot> blockedSlotsCountingTowardsFullSet;
	private String statusEffectId;
	private StatusEffect associatedStatusEffect;

	public AbstractSetBonus(String name, StatusEffect associatedStatusEffect, int numberRequiredForCompleteSet, List<InventorySlot> blockedSlotsCountingTowardsFullSet) {
		this.name = name;
		this.numberRequiredForCompleteSet = numberRequiredForCompleteSet;
		
		if(blockedSlotsCountingTowardsFullSet==null) {
			this.blockedSlotsCountingTowardsFullSet = new ArrayList<>();
		} else {
			this.blockedSlotsCountingTowardsFullSet = blockedSlotsCountingTowardsFullSet;
		}
		
		this.associatedStatusEffect = associatedStatusEffect;
	}
	
	public AbstractSetBonus(File XMLFile, String author, boolean mod) {
		if (XMLFile.exists()) {
			try {
				Document doc = Main.getDocBuilder().parse(XMLFile);
				
				// Cast magic:
				doc.getDocumentElement().normalize();
				
				Element coreElement = Element.getDocumentRootElement(XMLFile); // Loads the document and returns the root element - in setBonus files it's <setBonus>
				
				this.mod = mod;
				
				this.name = coreElement.getMandatoryFirstOf("name").getTextContent();
				
				this.numberRequiredForCompleteSet = Integer.valueOf(coreElement.getMandatoryFirstOf("numberRequiredForCompleteSet").getTextContent());
				
				this.statusEffectId = coreElement.getMandatoryFirstOf("statusEffect").getTextContent();

				this.blockedSlotsCountingTowardsFullSet = new ArrayList<>();
				if(coreElement.getOptionalFirstOf("blockedSlotsCountingTowardsFullSet").isPresent()) {
					for(Element e : coreElement.getMandatoryFirstOf("blockedSlotsCountingTowardsFullSet").getAllOf("slot")) {
						InventorySlot slot = InventorySlot.valueOf(e.getTextContent());
						this.blockedSlotsCountingTowardsFullSet.add(slot);
					};
				}
				
			} catch(Exception ex) {
				ex.printStackTrace();
				System.err.println("SetBonus was unable to be loaded from file! (" + XMLFile.getName() + ")\n" + ex);
			}
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean isMod() {
		return mod;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getNumberRequiredForCompleteSet() {
		return numberRequiredForCompleteSet;
	}

	@Override
	public StatusEffect getAssociatedStatusEffect() {
		if(associatedStatusEffect==null) {
			associatedStatusEffect = StatusEffect.getStatusEffectFromId(statusEffectId);
		}
		return associatedStatusEffect;
	}

	@Override
	public List<InventorySlot> getBlockedSlotsCountingTowardsFullSet() {
		return blockedSlotsCountingTowardsFullSet;
	}
}
