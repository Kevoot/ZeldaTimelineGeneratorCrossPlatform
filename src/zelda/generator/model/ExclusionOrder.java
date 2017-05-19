package zelda.generator.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;

public enum ExclusionOrder implements Serializable
{
	NoData,
	MustBeAfter,
	MustBeBefore,
	CantBeAfter,
	CantBeBefore;

	public static final int SIZE = Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static ExclusionOrder forValue(int value)
	{
		return values()[value];
	}

	public static ObservableList<ExclusionOrder> getOrderList() {
        return new SimpleListProperty<ExclusionOrder>(FXCollections.observableArrayList(values()));
    }

	public static ObservableList<String> getFormattedOrderList() {
		ObservableList<String> converted = FXCollections.observableArrayList();
		for(ExclusionOrder order : values()) {
			converted.add(getStringFromEnum(order));
		}
		return new SimpleListProperty<String>(FXCollections.observableArrayList(converted));
	}

    public ListProperty<ExclusionOrder> orderListProperty() {
        return new SimpleListProperty<ExclusionOrder>(FXCollections.observableArrayList(values()));
    }

    public static String getStringFromEnum(ExclusionOrder order) {
    	switch(order) {
	    	case MustBeAfter:
	    		return "Must Be After";
	    	case MustBeBefore:
	    		return "Must Be Before";
	    	case CantBeBefore:
	    		return "Can't Be Before";
	    	case CantBeAfter:
	    		return "Can't Be After";
	    	default:
	    		return "No Data";
    	}
    }

    public static ExclusionOrder getEnumFromString(String string) {
    	switch(string) {
	    	case "Must Be After":
	    		return MustBeAfter;
	    	case "Must Be Before":
	    		return MustBeBefore;
	    	case "Can't Be Before":
	    		return CantBeBefore;
	    	case "Can't Be After":
	    		return CantBeAfter;
	    	default:
	    		return NoData;
    	}
    }
}