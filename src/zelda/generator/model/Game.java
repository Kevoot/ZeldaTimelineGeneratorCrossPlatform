package zelda.generator.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable
{
	private static final long serialVersionUID = 2306807905564283053L;
	private final transient ObjectProperty<GameEnum> gameTitle;
	private final transient ListProperty<Connection> connections;
	private final transient StringProperty numConnections;
	public String serializableGameTitle;
	public List<Connection> serializableConnections;
	public String serializableNumConnections;

	public Game()
	{
		this.gameTitle = new SimpleObjectProperty(GameEnum.NoData);
		this.connections =new SimpleListProperty<Connection>(FXCollections.observableArrayList());
		this.numConnections = new SimpleStringProperty("0");
	}
	public Game(GameEnum gameTitle)
	{
		this.gameTitle = new SimpleObjectProperty<GameEnum>(gameTitle);
		this.connections = new SimpleListProperty<Connection>(FXCollections.observableArrayList());
		this.numConnections = new SimpleStringProperty("0");
	}
	public Game(GameEnum sourceGame, ObservableList<Connection> connections)
	{
		this.gameTitle = new SimpleObjectProperty(sourceGame);
		this.connections = new SimpleListProperty<Connection>(FXCollections.observableArrayList(connections));
		this.numConnections = new SimpleStringProperty(String.valueOf(getConnections().size()));
	}
	// For deserializing
	public Game(String serializedGameTitle, List<Connection> serializedConnections)
	{
		this.gameTitle = new SimpleObjectProperty(GameEnum.getEnumFromString(serializedGameTitle));
		List<Connection> deserializedConnections = new ArrayList<Connection>();
		for(Connection connection : serializedConnections) {
			deserializedConnections.add(
					new Connection(GameEnum.getEnumFromString(connection.serializableSourceGameTitle),
					(ExclusionOrder.getEnumFromString(connection.serializableOrder)),
					(GameEnum.getEnumFromString(connection.serializableTargetGameTitle)),
					connection.serializableRating, connection.serializableEvidenceDescriptionText));
		}
		this.connections = new SimpleListProperty<Connection>(FXCollections.observableArrayList(deserializedConnections));
		this.numConnections = new SimpleStringProperty(String.valueOf(getConnections().size()));
	}

	public void prepareSerialize() {
		serializableGameTitle = GameEnum.getStringFromEnum(this.gameTitle.getValue());
		serializableConnections = new ArrayList<Connection>();
		for(Connection connection : this.connections) {
			connection.prepareSerialize();
			serializableConnections.add(connection);
		}
		serializableNumConnections = this.getNumConnections();
	}

	public GameEnum getGameTitle() {
        return gameTitle.get();
    }

	public String getGameTitleString() {
        return gameTitle.get().toString();
    }

    public void setGameTitle(GameEnum gameTitle) {
        this.gameTitle.set(gameTitle);
    }

    public ObjectProperty<GameEnum> gameTitleProperty() {
        return gameTitle;
    }

	public StringProperty gameTitleStringProperty() {
        return new SimpleStringProperty(gameTitle.get().toString());
    }

	public StringProperty gameTitleFormattedStringProperty() {
        return new SimpleStringProperty(GameEnum.getStringFromEnum(gameTitle.getValue()));
    }

	public ObservableList<Connection> getConnections() {
        return connections;
    }

    public void setConnections(ObservableList<Connection> connections) {
        this.connections.setAll(connections);
        this.setNumConnections(String.valueOf(this.connections.getSize()));
    }

    public ListProperty<Connection> connectionsProperty() {
        return connections;
    }

	public String getNumConnections() {
        return numConnections.get();
    }

	public void setNumConnections(String numConnections) {
		this.numConnections.set(numConnections);
	}

    public StringProperty numConnectionsStringProperty() {
        return new SimpleStringProperty(String.valueOf(getConnections().size()));
    }
}