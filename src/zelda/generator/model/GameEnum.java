package zelda.generator.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;

public enum GameEnum implements Serializable
{
	NoData,
	TheLegendOfZelda,
	Zelda2TheAdventureofLink,
	ALinkToThePast,
	BSZeldaAncientStoneTablets,
	LinksAwakening,
	OcarinaofTime,
	MajorasMask,
	OoX,
	TheWindWaker,
	FourSwords,
	FourSwordsAdventures,
	TwilightPrincess,
	TwilightPrincessWii,
	TheMinishCap,
	SkywardSword,
	PhantomHourglass,
	SpiritTracks,
	ALinkBetweenWorlds,
	TriForceHeroes,
	BreathOfTheWild,
	LinksAwakeningDX,
	OcarinaOfTimeMasterQuest,
	OcarinaOfTime3D,
	MajorasMask3D,
	TheWindWakerHD,
	TwilightPrincessHD,
	ZeldaWandofGamelon,
	LinkTheFacesOfEvil,
	ZeldasAdventure,
	LinksCrossbowTraining,
	HyruleWarriors,
	HyruleWarriorsLegends;

	public int getValue()
	{
		return this.ordinal();
	}

	public static String getStringFromEnum(GameEnum title) {
		switch(title) {
		case TheLegendOfZelda:
			return "The Legend of Zelda";
		case Zelda2TheAdventureofLink:
			return "Zelda II: The Adventure of Link";
		case ALinkToThePast:
			return "A Link to the Past";
		case BSZeldaAncientStoneTablets:
			return "BS Zelda: Ancient Stone Tablets";
		case LinksAwakening:
			return "Link's Awakening";
		case OcarinaofTime:
			return "Ocarina of Time";
		case MajorasMask:
			return "Majora's Mask";
		case OoX:
			return "Oracle of Ages/Seasons";
		case TheWindWaker:
			return "The Wind Waker";
		case FourSwords:
			return "Four Swords";
		case FourSwordsAdventures:
			return "Four Swords Adventures";
		case TwilightPrincess:
			return "Twilight Princess";
		case TwilightPrincessWii:
			return "Twilight Princess (Wii)";
		case TheMinishCap:
			return "The Minish Cap";
		case SkywardSword:
			return "Skyward Sword";
		case PhantomHourglass:
			return "Phantom Hourglass";
		case SpiritTracks:
			return "Spirit Tracks";
		case ALinkBetweenWorlds:
			return "A Link Between Worlds";
		case TriForceHeroes:
			return "Triforce Heroes";
		case BreathOfTheWild:
			return "Breath of the Wild";
		case LinksAwakeningDX:
			return "Link's Awakening DX";
		case OcarinaOfTimeMasterQuest:
			return "Ocarina of Time Master Quest";
		case OcarinaOfTime3D:
			return "Ocarina of Time 3D";
		case MajorasMask3D:
			return "Majora's Mask 3D";
		case TheWindWakerHD:
			return "The Wind Waker HD";
		case TwilightPrincessHD:
			return "Twilight Princess HD";
		case ZeldaWandofGamelon:
			return "Zelda: Wand of Gamelon";
		case LinkTheFacesOfEvil:
			return "Link: The Faces of Evil";
		case ZeldasAdventure:
			return "Zelda's Adventure";
		case LinksCrossbowTraining:
			return "Link's Crossbow Training";
		case HyruleWarriors:
			return "Hyrule Warriors";
		case HyruleWarriorsLegends:
			return "Hyrule Warriors Legends";
		default:
			return "No Data";
		}
	}

	public static GameEnum getEnumFromString(String string) {
		switch(string) {
		case "The Legend of Zelda":
			return TheLegendOfZelda;
		case "Zelda II: The Adventure of Link":
			return Zelda2TheAdventureofLink;
		case "A Link to the Past":
			return ALinkToThePast;
		case "BS Zelda: Ancient Stone Tablets":
			return BSZeldaAncientStoneTablets;
		case "Link's Awakening":
			return LinksAwakening;
		case "Ocarina of Time":
			return OcarinaofTime;
		case "Majora's Mask":
			return MajorasMask;
		case "Oracle of Ages/Seasons":
			return OoX;
		case "The Wind Waker":
			return TheWindWaker;
		case "Four Swords":
			return FourSwords;
		case "Four Swords Adventures":
			return FourSwordsAdventures;
		case "Twilight Princess":
			return TwilightPrincess;
		case "Twilight Princess (Wii)":
			return TwilightPrincessWii;
		case "The Minish Cap":
			return TheMinishCap;
		case "Skyward Sword":
			return SkywardSword;
		case "Phantom Hourglass":
			return PhantomHourglass;
		case "Spirit Tracks":
			return SpiritTracks;
		case "A Link Between Worlds":
			return ALinkBetweenWorlds;
		case "Triforce Heroes":
			return TriForceHeroes;
		case "Breath of the Wild":
			return BreathOfTheWild;
		case "Link's Awakening DX":
			return LinksAwakeningDX;
		case "Ocarina of Time Master Quest":
			return OcarinaOfTimeMasterQuest;
		case "Ocarina of Time 3D":
			return OcarinaOfTime3D;
		case "Majora's Mask 3D":
			return MajorasMask3D;
		case "The Wind Waker HD":
			return TheWindWakerHD;
		case "Twilight Princess HD":
			return TwilightPrincessHD;
		case "Zelda: Wand of Gamelon":
			return ZeldaWandofGamelon;
		case "Link: The Faces of Evil":
			return LinkTheFacesOfEvil;
		case "Zelda's Adventure":
			return ZeldasAdventure;
		case "Link's Crossbow Training":
			return LinksCrossbowTraining;
		case "Hyrule Warriors":
			return HyruleWarriors;
		case "Hyrule Warriors Legends":
			return HyruleWarriorsLegends;
		default:
			return NoData;
		}
	}

	public static GameEnum forValue(int value)
	{
		return values()[value];
	}

	public static ObservableList<String> getFormattedTitleList() {
		ObservableList<String> converted = FXCollections.observableArrayList();
		for(GameEnum game : values()) {
			converted.add(getStringFromEnum(game));
		}
		return new SimpleListProperty<String>(FXCollections.observableArrayList(converted));
	}

	public static ObservableList<GameEnum> getTitleList() {
        return new SimpleListProperty<GameEnum>(FXCollections.observableArrayList(values()));
    }

    public ListProperty<GameEnum> titleListProperty() {
        return new SimpleListProperty<GameEnum>(FXCollections.observableArrayList(values()));
    }
}