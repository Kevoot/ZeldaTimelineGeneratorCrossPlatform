package zelda.generator.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Connection implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = -7584569347786912651L;
	private final transient ObjectProperty<GameEnum> sourceGameTitle;
	private final transient ObjectProperty<ExclusionOrder> order;
	private final transient ObjectProperty<GameEnum> targetGameTitle;
	private final transient StringProperty rating;
	private final transient StringProperty evidenceDescriptionText;
	public String serializableSourceGameTitle;
	public String serializableOrder;
	public String serializableTargetGameTitle;
	public String serializableRating;
	public String serializableEvidenceDescriptionText;

	public Connection() {
		this(null);
	}

	public Connection(GameEnum sourceGameTitle) {
		this.sourceGameTitle = new SimpleObjectProperty<GameEnum>(sourceGameTitle);
		this.order = new SimpleObjectProperty<ExclusionOrder>(ExclusionOrder.NoData);
		this.targetGameTitle = new SimpleObjectProperty<GameEnum>(GameEnum.NoData);
		this.rating = new SimpleStringProperty("0");
		this.evidenceDescriptionText = new SimpleStringProperty("NoData");
		serializableSourceGameTitle = "NoData";
		serializableOrder = "NoData";
		serializableTargetGameTitle = "NoData";
		serializableRating = "0";
		serializableEvidenceDescriptionText = "NoData";
	}

	// For deserializing
	public Connection(GameEnum sourceGameTitle, ExclusionOrder order, GameEnum targetGameTitle, String rating, String text) {
		this.sourceGameTitle = new SimpleObjectProperty<GameEnum>(sourceGameTitle);
		this.order = new SimpleObjectProperty<ExclusionOrder>(order);
		this.targetGameTitle = new SimpleObjectProperty<GameEnum>(targetGameTitle);
		this.rating = new SimpleStringProperty(rating);
		this.evidenceDescriptionText = new SimpleStringProperty(text);
		serializableSourceGameTitle = "NoData";
		serializableOrder = "NoData";
		serializableTargetGameTitle = "NoData";
		serializableRating = "0";
		serializableEvidenceDescriptionText = "NoData";
	}

	public void prepareSerialize() {
		serializableSourceGameTitle = GameEnum.getStringFromEnum(sourceGameTitle.getValue());
		serializableOrder = ExclusionOrder.getStringFromEnum(order.getValue());
		serializableTargetGameTitle = GameEnum.getStringFromEnum(targetGameTitle.getValue());
		serializableRating = rating.getValue();
		serializableEvidenceDescriptionText = evidenceDescriptionText.getValue();
	}

    public GameEnum getSourceGameTitle() {
        return sourceGameTitle.get();
    }

    public void setSourceGameTitle(GameEnum sourceGameTitle) {
        this.sourceGameTitle.set(sourceGameTitle);
    }

    public ObjectProperty<GameEnum> sourceGameTitleProperty() {
        return sourceGameTitle;
    }

    public StringProperty sourceGameTitleStringProperty() {
        return new SimpleStringProperty(String.valueOf(sourceGameTitle));
    }

    public StringProperty sourceGameTitleFormattedStringProperty() {
        return new SimpleStringProperty(GameEnum.getStringFromEnum(getSourceGameTitle()));
    }

    public ExclusionOrder getOrder() {
        return order.get();
    }

    public void setOrder(ExclusionOrder order) {
        this.order.set(order);
    }

    public ObjectProperty<ExclusionOrder> orderProperty() {
        return order;
    }

    public StringProperty orderStringProperty() {
        return new SimpleStringProperty(String.valueOf(order));
    }

    public StringProperty orderFormattedStringProperty() {
    	return new SimpleStringProperty(ExclusionOrder.getStringFromEnum(getOrder()));
    }

    public GameEnum getTargetGameTitle() {
        return targetGameTitle.get();
    }

    public void setTargetGameTitle(GameEnum targetGameTitle) {
        this.targetGameTitle.set(targetGameTitle);
    }

    public ObjectProperty<GameEnum> targetGameTitleProperty() {
        return targetGameTitle;
    }

    public StringProperty targetGameTitleStringProperty() {
        return new SimpleStringProperty(String.valueOf(targetGameTitle));
    }

    public StringProperty targetGameTitleFormattedStringProperty() {
    	return new SimpleStringProperty(GameEnum.getStringFromEnum(getTargetGameTitle()));
    }

    public String getRating() {
        return rating.get();
    }

    public String getRatingString() {
        return String.valueOf(rating.get());
    }

    public void setRating(String rating) {
        this.rating.set(rating);
    }

    public StringProperty ratingProperty() {
        return rating;
    }

    public StringProperty ratingStringProperty() {
        return new SimpleStringProperty(String.valueOf(rating));
    }

    public String getEvidenceDescriptionText() {
        return evidenceDescriptionText.get();
    }

    public void setEvidenceDescriptionText(String evidenceDescriptionText) {
        this.evidenceDescriptionText.set(evidenceDescriptionText);
    }

    public StringProperty evidenceDescriptionTextProperty() {
        return evidenceDescriptionText;
    }
}
