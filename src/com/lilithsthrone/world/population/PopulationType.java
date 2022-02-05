package com.lilithsthrone.world.population;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;

import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

/**
 * @since 0.2.12
 * @version 0.3.9
 * @author Innoxia
 */
public class PopulationType {

	private final String singular;
	private final String plural;

	public static PopulationType PERSON = new PopulationType("person", "people");

	public static PopulationType FAN = new PopulationType("fan", "fans");
	
	public static PopulationType HARPY = new PopulationType("harpy", "harpies") {
		@Override
		public String getName() {
			if(Main.game.isSillyModeEnabled()) {
				return "birb";
			}
			return "harpy";
		}
		@Override
		public String getNamePlural() {
			if(Main.game.isSillyModeEnabled()) {
				return "birbs";
			}
			return "harpies";
		}
	};
	
	public static PopulationType CROWD = new PopulationType("crowd", "crowds");

	public static PopulationType PRIVATE_SECURITY_GUARD = new PopulationType("private security guard", "private security guards");
	
	public static PopulationType ENFORCER = new PopulationType("Enforcer", "Enforcers");

	public static PopulationType CENTAUR_CARTS = new PopulationType("centaur-pulled cart", "centaur-pulled carts");
	
	public static PopulationType SHOPPER = new PopulationType("shopper", "shoppers");
	
	public static PopulationType DINER = new PopulationType("diner", "diners");

	public static PopulationType VIP = new PopulationType("VIP", "VIPs");
	
	public static PopulationType GUARD = new PopulationType("guard", "guards");

	public static PopulationType MAID = new PopulationType("maid", "maids");

	public static PopulationType CHEF = new PopulationType("chef", "chefs");

	public static PopulationType SLAVE = new PopulationType("slave", "slaves");
	
	public static PopulationType OFFICE_WORKER = new PopulationType("office worker", "office workers");
	
	public static PopulationType TEXTILE_WORKER = new PopulationType("textile worker", "textile workers");
	
	public static PopulationType CONSTRUCTION_WORKER = new PopulationType("construction worker", "construction workers");
	
	public static PopulationType RECEPTIONIST = new PopulationType("receptionist", "receptionists");

	public static PopulationType GANG_MEMBER = new PopulationType("gang member", "gang members");

	public static PopulationType STALL_HOLDER = new PopulationType("stallholder", "stallholders");

	public static PopulationType MILKER = new PopulationType("milker", "milkers");
	
	public static PopulationType CASHIER = new PopulationType("cashier", "cashiers");
	
	public static PopulationType MASSEUSE = new PopulationType("masseuse", "masseuses");

	public PopulationType(String s, String p) {
		singular = s;
		plural = p;
	}

	public String getName() {
		return singular;
	}

	public String getNamePlural() {
		return plural;
	}

	public static List<PopulationType> getAllPopulationTypes() {
		return stream(PopulationType.class.getFields())
		.filter(f->PopulationType.class.isAssignableFrom(f.getType()))
		.filter(f->isStatic(f.getModifiers()))
		.map(f->{try{return(PopulationType)f.get(null);}catch(IllegalAccessException x){return null;}})
		.filter(Objects::nonNull)
		.collect(toUnmodifiableList());
	}
	
	public static boolean hasId(String id) {
		try {
			var f = PopulationType.class.getField(id);
			return isStatic(f.getModifiers()) && PopulationType.class.isAssignableFrom(f.getType());
		}
		catch(NoSuchFieldException x) {
			return false;
		}
	}
	
	public static PopulationType getPopulationTypeFromId(String id) {
		String match = Util.getClosestStringMatch(id,
			stream(PopulationType.class.getFields())
			.filter(f->PopulationType.class.isAssignableFrom(f.getType()))
			.filter(f->isStatic(f.getModifiers()))
			.map(Field::getName)
			.collect(toList()));
		try {
			return (PopulationType)PopulationType.class.getField(match).get(null);
		}
		catch(NoSuchFieldException|IllegalAccessException x) {
			x.printStackTrace();
			return null;
		}
	}

	public static String getIdFromPopulationType(PopulationType populationType) {
		return stream(PopulationType.class.getFields())
		.filter(f->PopulationType.class.isAssignableFrom(f.getType()))
		.filter(f->isStatic(f.getModifiers()))
		.filter(f->{try{return f.get(null).equals(populationType);}catch(IllegalAccessException x){return false;}})
		.map(Field::getName)
		.findAny().orElse(null);
	}
}
