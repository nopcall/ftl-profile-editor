package net.blerf.ftl.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import net.blerf.ftl.constants.Difficulty;
import net.blerf.ftl.parser.DataManager;
import net.blerf.ftl.parser.SavedGameParser;
import net.blerf.ftl.ui.FieldEditorPanel;
import net.blerf.ftl.ui.FTLFrame;
import net.blerf.ftl.ui.StatusbarMouseListener;
import net.blerf.ftl.xml.DroneBlueprint;
import net.blerf.ftl.xml.ShipBlueprint;
import net.blerf.ftl.xml.WeaponBlueprint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SavedGameGeneralPanel extends JPanel {

	private static final Logger log = LogManager.getLogger(SavedGameGeneralPanel.class);

	private static final String TOTAL_SHIPS_DEFEATED = "Total Ships Defeated";
	private static final String TOTAL_BEACONS = "Total Beacons";
	private static final String TOTAL_SCRAP = "Total Scrap";
	private static final String TOTAL_CREW_HIRED = "Total Crew Hired";
	private static final String DLC = "DLC";
	private static final String DIFFICULTY = "Difficulty";
	private static final String TOP_BETA = "Beta?";

	private static final String SECTOR_TREE_SEED = "Sector Tree Seed";
	private static final String SECTOR_NUMBER = "Sector Number";
	private static final String SECTOR_LAYOUT_SEED = "Sector Layout Seed";
	private static final String REBEL_FLEET_OFFSET = "Rebel Fleet Offset";
	private static final String REBEL_FLEET_FUDGE = "Rebel Fleet Fudge";
	private static final String REBEL_PURSUIT_MOD = "Rebel Pursuit Mod";
	private static final String HIDDEN_SECTOR = "In Hidden Sector";
	private static final String HAZARDS_VISIBLE = "Hazards Visible";

	private static final String CARGO_ONE = "#1";
	private static final String CARGO_TWO = "#2";
	private static final String CARGO_THREE = "#3";
	private static final String CARGO_FOUR = "#4";
	private static final String[] cargoSlots = new String[] { CARGO_ONE, CARGO_TWO, CARGO_THREE, CARGO_FOUR };

	private static final String FLAGSHIP = "Flagship Visible";
	private static final String FLAGSHIP_HOP = "Flagship Hop";
	private static final String FLAGSHIP_MOVING = "Flagship Moving";
	private static final String FLAGSHIP_BASE_TURNS = "Flagship Base Turns";

	private static final String TOP_GAMMA = "Gamma?";
	private static final String TOP_DELTA = "Delta?";
	private static final String TOP_EPSILON = "Epsilon?";
	private static final String TOP_KAPPA = "Kappa?";
	private static final String TOP_MU = "Mu?";
	private static final String TOP_NU = "Nu?";

	private static final String ZEUS_EPSILON = "Epsilon?";
	private static final String ZEUS_ZETA = "Zeta?";
	private static final String ZEUS_AUTOFIRE = "Autofire";
	private static final String ZEUS_ETA = "Eta?";
	private static final String ZEUS_IOTA = "Iota?";
	private static final String ZEUS_KAPPA = "Kappa?";

	private FTLFrame frame;

	private FieldEditorPanel sessionPanel = null;
	private FieldEditorPanel sectorPanel = null;
	private FieldEditorPanel cargoPanel = null;
	private FieldEditorPanel bossPanel = null;
	private FieldEditorPanel unknownsPanel = null;
	private FieldEditorPanel zeusPanel = null;

	private boolean zeusEnabled = true;


	public SavedGameGeneralPanel( FTLFrame frame ) {
		this.setLayout( new GridBagLayout() );

		this.frame = frame;

		sessionPanel = new FieldEditorPanel( true );
		sessionPanel.setBorder( BorderFactory.createTitledBorder("Session") );
		sessionPanel.addRow( TOTAL_SHIPS_DEFEATED, FieldEditorPanel.ContentType.INTEGER );
		sessionPanel.addRow( TOTAL_BEACONS, FieldEditorPanel.ContentType.INTEGER );
		sessionPanel.addRow( TOTAL_SCRAP, FieldEditorPanel.ContentType.INTEGER );
		sessionPanel.addRow( TOTAL_CREW_HIRED, FieldEditorPanel.ContentType.INTEGER );
		sessionPanel.addRow( DLC, FieldEditorPanel.ContentType.BOOLEAN );
		sessionPanel.addRow( DIFFICULTY, FieldEditorPanel.ContentType.COMBO );
		sessionPanel.addRow( TOP_BETA, FieldEditorPanel.ContentType.INTEGER );
		sessionPanel.getInt(TOP_BETA).setDocument( new RegexDocument("-?[0-9]*") );
		sessionPanel.addBlankRow();
		sessionPanel.addFillRow();

		sessionPanel.getBoolean(DLC).addMouseListener( new StatusbarMouseListener(frame, "Toggle FTL:AE content (changing to false may be dangerous).") );
		sessionPanel.getCombo(DIFFICULTY).addMouseListener( new StatusbarMouseListener(frame, "Difficulty (FTL 1.01-1.03.3 did not have HARD).") );
		sessionPanel.getInt(TOP_BETA).addMouseListener( new StatusbarMouseListener(frame, "Unknown session field. Always 0?") );

		sectorPanel = new FieldEditorPanel( true );
		sectorPanel.setBorder( BorderFactory.createTitledBorder("Sector") );
		sectorPanel.addRow( SECTOR_TREE_SEED, FieldEditorPanel.ContentType.INTEGER );
		sectorPanel.getInt(SECTOR_TREE_SEED).setDocument( new RegexDocument("-?[0-9]*") );
		sectorPanel.addRow( SECTOR_NUMBER, FieldEditorPanel.ContentType.SLIDER );
		sectorPanel.getSlider(SECTOR_NUMBER).setMaximum(0);
		sectorPanel.addBlankRow();
		sectorPanel.addRow( SECTOR_LAYOUT_SEED, FieldEditorPanel.ContentType.INTEGER );
		sectorPanel.getInt(SECTOR_LAYOUT_SEED).setDocument( new RegexDocument("-?[0-9]*") );
		sectorPanel.addRow( REBEL_FLEET_OFFSET, FieldEditorPanel.ContentType.INTEGER );
		sectorPanel.getInt(REBEL_FLEET_OFFSET).setDocument( new RegexDocument("-?[0-9]*") );
		sectorPanel.addRow( REBEL_FLEET_FUDGE, FieldEditorPanel.ContentType.INTEGER );
		sectorPanel.getInt(REBEL_FLEET_FUDGE).setDocument( new RegexDocument("-?[0-9]*") );
		sectorPanel.addRow( REBEL_PURSUIT_MOD, FieldEditorPanel.ContentType.INTEGER );
		sectorPanel.getInt(REBEL_PURSUIT_MOD).setDocument( new RegexDocument("-?[0-9]*") );
		sectorPanel.addRow( HIDDEN_SECTOR, FieldEditorPanel.ContentType.BOOLEAN );
		sectorPanel.addRow( HAZARDS_VISIBLE, FieldEditorPanel.ContentType.BOOLEAN );
		sectorPanel.addBlankRow();
		sectorPanel.addFillRow();

		sectorPanel.getInt(SECTOR_TREE_SEED).addMouseListener( new StatusbarMouseListener(frame, "A per-game constant that seeds the random generation of the sector tree (dangerous). Roll back to sector 1 if you change this!") );
		sectorPanel.getSlider(SECTOR_NUMBER).addMouseListener( new StatusbarMouseListener(frame, "Roll back to a previous sector.") );
		sectorPanel.getInt(SECTOR_LAYOUT_SEED).addMouseListener( new StatusbarMouseListener(frame, "A per-sector constant that seeds the random generation of the map, events, etc. (potentially dangerous).") );
		sectorPanel.getInt(REBEL_FLEET_OFFSET).addMouseListener( new StatusbarMouseListener(frame, "A large negative var (-750,-250,...,-n*25, approaching 0) + fudge = the fleet circle's leading edge.") );
		sectorPanel.getInt(REBEL_FLEET_FUDGE).addMouseListener( new StatusbarMouseListener(frame, "A random per-sector constant (usually around 75-310) + offset = the fleet circle's edge.") );
		sectorPanel.getInt(REBEL_PURSUIT_MOD).addMouseListener( new StatusbarMouseListener(frame, "Delay/alert the fleet, changing the warning zone thickness (e.g., merc distraction = -2).") );
		sectorPanel.getBoolean(HIDDEN_SECTOR).addMouseListener( new StatusbarMouseListener(frame, "Sector #?: Hidden Crystal Worlds. At the exit, you won't get to choose the next sector.") );
		sectorPanel.getBoolean(HAZARDS_VISIBLE).addMouseListener( new StatusbarMouseListener(frame, "Show hazards on the current sector map.") );

		cargoPanel = new FieldEditorPanel( false );
		cargoPanel.setBorder( BorderFactory.createTitledBorder("Cargo") );

		for (int i=0; i < cargoSlots.length; i++) {
			cargoPanel.addRow( cargoSlots[i], FieldEditorPanel.ContentType.COMBO );
		}
		cargoPanel.addBlankRow();
		cargoPanel.addFillRow();

		bossPanel = new FieldEditorPanel( true );
		bossPanel.setBorder( BorderFactory.createTitledBorder("Boss") );
		bossPanel.addRow( FLAGSHIP, FieldEditorPanel.ContentType.BOOLEAN );
		bossPanel.addRow( FLAGSHIP_HOP, FieldEditorPanel.ContentType.SLIDER );
		bossPanel.getSlider(FLAGSHIP_HOP).setMaximum( 10 );
		bossPanel.addRow( FLAGSHIP_MOVING, FieldEditorPanel.ContentType.BOOLEAN );
		bossPanel.addRow( FLAGSHIP_BASE_TURNS, FieldEditorPanel.ContentType.INTEGER );
		bossPanel.addBlankRow();
		bossPanel.addFillRow();

		bossPanel.getBoolean(FLAGSHIP).addMouseListener( new StatusbarMouseListener(frame, "Toggle the rebel flagship. (FTL 1.01-1.03.3: Instant loss if not in sector 8.)") );
		bossPanel.getSlider(FLAGSHIP_HOP).addMouseListener( new StatusbarMouseListener(frame, "The flagship is at it's Nth random beacon. (0-based) Sector layout seed affects where that will be. (FTL 1.01-1.03.3: Instant loss may occur beyond 4.)") );
		bossPanel.getBoolean(FLAGSHIP_MOVING).addMouseListener( new StatusbarMouseListener(frame, "The flagship is moving from its current beacon toward the next.") );
		bossPanel.getInt(FLAGSHIP_BASE_TURNS).addMouseListener( new StatusbarMouseListener(frame, "Number of turns the flagship has started at the fed base. Instant loss will occur beyond 3.") );

		bossPanel.getBoolean(FLAGSHIP).addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				boolean flagshipVisible = bossPanel.getBoolean(FLAGSHIP).isSelected();
				if ( !flagshipVisible ) {
					bossPanel.getSlider(FLAGSHIP_HOP).setValue( 0 );
					bossPanel.getBoolean(FLAGSHIP_MOVING).setSelected( false );
					bossPanel.getInt(FLAGSHIP_BASE_TURNS).setText( "0" );
				}
				bossPanel.getSlider(FLAGSHIP_HOP).setEnabled( flagshipVisible );
				bossPanel.getBoolean(FLAGSHIP_MOVING).setEnabled( flagshipVisible );
				bossPanel.getInt(FLAGSHIP_BASE_TURNS).setEnabled( flagshipVisible );
			}
		});

		unknownsPanel = new FieldEditorPanel( true );
		unknownsPanel.setBorder( BorderFactory.createTitledBorder("Top-Level Unknowns") );
		unknownsPanel.addRow( TOP_GAMMA, FieldEditorPanel.ContentType.INTEGER );
		unknownsPanel.getInt(TOP_GAMMA).setDocument( new RegexDocument("-?[0-9]*") );
		unknownsPanel.addRow( TOP_DELTA, FieldEditorPanel.ContentType.INTEGER );
		unknownsPanel.getInt(TOP_DELTA).setDocument( new RegexDocument("-?[0-9]*") );
		unknownsPanel.addRow( TOP_EPSILON, FieldEditorPanel.ContentType.INTEGER );
		unknownsPanel.getInt(TOP_EPSILON).setDocument( new RegexDocument("-?[0-9]*") );
		unknownsPanel.addRow( TOP_KAPPA, FieldEditorPanel.ContentType.INTEGER );
		unknownsPanel.getInt(TOP_KAPPA).setDocument( new RegexDocument("-?[0-9]*") );
		unknownsPanel.addRow( TOP_MU, FieldEditorPanel.ContentType.INTEGER );
		unknownsPanel.getInt(TOP_MU).setDocument( new RegexDocument("-?[0-9]*") );
		unknownsPanel.addRow( TOP_NU, FieldEditorPanel.ContentType.INTEGER );
		unknownsPanel.getInt(TOP_NU).setDocument( new RegexDocument("-?[0-9]*") );
		unknownsPanel.addBlankRow();
		unknownsPanel.addFillRow();

		unknownsPanel.getInt(TOP_GAMMA).addMouseListener( new StatusbarMouseListener(frame, "Unknown.") );
		unknownsPanel.getInt(TOP_DELTA).addMouseListener( new StatusbarMouseListener(frame, "Unknown. Often -1. Sometimes thousands. Ticks for something?") );
		unknownsPanel.getInt(TOP_EPSILON).addMouseListener( new StatusbarMouseListener(frame, "Unknown.") );
		unknownsPanel.getInt(TOP_KAPPA).addMouseListener( new StatusbarMouseListener(frame, "Unknown. Maybe flagship-related?") );
		unknownsPanel.getInt(TOP_MU).addMouseListener( new StatusbarMouseListener(frame, "Unknown.") );
		unknownsPanel.getInt(TOP_NU).addMouseListener( new StatusbarMouseListener(frame, "Unknown. Only set when a nearby ship is present. During flagship fights, it's 1.") );

		zeusPanel = new FieldEditorPanel( true );
		zeusPanel.setBorder( BorderFactory.createTitledBorder("Zeus?") );
		zeusPanel.addRow( ZEUS_EPSILON, FieldEditorPanel.ContentType.INTEGER );
		zeusPanel.getInt(ZEUS_EPSILON).setDocument( new RegexDocument("-?[0-9]*") );
		zeusPanel.addRow( ZEUS_ZETA, FieldEditorPanel.ContentType.INTEGER );
		zeusPanel.getInt(ZEUS_ZETA).setDocument( new RegexDocument("-?[0-9]*") );
		zeusPanel.addRow( ZEUS_AUTOFIRE, FieldEditorPanel.ContentType.BOOLEAN );
		zeusPanel.addRow( ZEUS_ETA, FieldEditorPanel.ContentType.INTEGER );
		zeusPanel.getInt(ZEUS_ETA).setDocument( new RegexDocument("-?[0-9]*") );
		zeusPanel.addRow( ZEUS_IOTA, FieldEditorPanel.ContentType.INTEGER );
		zeusPanel.getInt(ZEUS_IOTA).setDocument( new RegexDocument("-?[0-9]*") );
		zeusPanel.addRow( ZEUS_KAPPA, FieldEditorPanel.ContentType.INTEGER );
		zeusPanel.getInt(ZEUS_KAPPA).setDocument( new RegexDocument("-?[0-9]*") );

		zeusPanel.getInt(ZEUS_EPSILON).addMouseListener( new StatusbarMouseListener(frame, "Unknown. Probably a seed related to the player ship.") );
		zeusPanel.getInt(ZEUS_ZETA).addMouseListener( new StatusbarMouseListener(frame, "Unknown. Only set when a nearby ship is present.") );
		zeusPanel.getBoolean(ZEUS_AUTOFIRE).addMouseListener( new StatusbarMouseListener(frame, "Toggles autofire.") );
		zeusPanel.getInt(ZEUS_ETA).addMouseListener( new StatusbarMouseListener(frame, "Unknown.") );
		zeusPanel.getInt(ZEUS_IOTA).addMouseListener( new StatusbarMouseListener(frame, "Unknown.") );
		zeusPanel.getInt(ZEUS_KAPPA).addMouseListener( new StatusbarMouseListener(frame, "Unknown.") );

		GridBagConstraints thisC = new GridBagConstraints();
		thisC.fill = GridBagConstraints.NORTH;
		thisC.fill = GridBagConstraints.BOTH;
		thisC.weightx = 0.0;
		thisC.weighty = 0.0;
		thisC.gridx = 0;
		thisC.gridy = 0;
		this.add( sessionPanel, thisC );

		thisC.gridx++;
		this.add( cargoPanel, thisC );

		thisC.gridx = 0;
		thisC.gridy++;
		this.add( sectorPanel, thisC );

		thisC.gridx++;
		this.add( bossPanel, thisC );

		thisC.gridx = 0;
		thisC.gridy++;
		this.add( unknownsPanel, thisC );

		thisC.gridx++;
		this.add( zeusPanel, thisC );

		thisC.fill = GridBagConstraints.BOTH;
		thisC.weighty = 1.0;
		thisC.gridx = 0;
		thisC.gridy++;
		this.add( Box.createVerticalGlue(), thisC );

		setGameState( null );
	}

	public void setGameState( SavedGameParser.SavedGameState gameState ) {
		sectorPanel.getSlider(SECTOR_NUMBER).setMinimum( 0 );
		sectorPanel.getSlider(SECTOR_NUMBER).setMaximum( 0 );

		sessionPanel.reset();
		sectorPanel.reset();
		cargoPanel.reset();
		unknownsPanel.reset();

		if ( gameState != null ) {
			SavedGameParser.ShipState shipState = gameState.getPlayerShipState();
			ShipBlueprint shipBlueprint = DataManager.get().getShip( shipState.getShipBlueprintId() );
			if ( shipBlueprint == null )
				throw new RuntimeException( String.format("Could not find blueprint for%s ship: %s", (shipState.isAuto() ? " auto" : ""), shipState.getShipName()) );

			for ( Difficulty d : Difficulty.values() ) {
				sessionPanel.getCombo(DIFFICULTY).addItem( d );
			}

			sessionPanel.setIntAndReminder( TOTAL_SHIPS_DEFEATED, gameState.getTotalShipsDefeated() );
			sessionPanel.setIntAndReminder( TOTAL_BEACONS, gameState.getTotalBeaconsExplored() );
			sessionPanel.setIntAndReminder( TOTAL_SCRAP, gameState.getTotalScrapCollected() );
			sessionPanel.setIntAndReminder( TOTAL_CREW_HIRED, gameState.getTotalCrewHired() );
			sessionPanel.setBoolAndReminder( DLC, gameState.isDLCEnabled() );
			sessionPanel.setComboAndReminder( DIFFICULTY, gameState.getDifficulty() );
			sessionPanel.setIntAndReminder( TOP_BETA, gameState.getUnknownBeta() );

			sectorPanel.setIntAndReminder( SECTOR_TREE_SEED, gameState.getSectorTreeSeed() );
			sectorPanel.getSlider(SECTOR_NUMBER).setMaximum( gameState.getSectorNumber()+1 );
			sectorPanel.getSlider(SECTOR_NUMBER).setMinimum( 1 );
			sectorPanel.setSliderAndReminder( SECTOR_NUMBER, gameState.getSectorNumber()+1 );
			sectorPanel.setIntAndReminder( SECTOR_LAYOUT_SEED, gameState.getSectorLayoutSeed() );
			sectorPanel.setIntAndReminder( REBEL_FLEET_OFFSET, gameState.getRebelFleetOffset() );
			sectorPanel.setIntAndReminder( REBEL_FLEET_FUDGE, gameState.getRebelFleetFudge() );
			sectorPanel.setIntAndReminder( REBEL_PURSUIT_MOD, gameState.getRebelPursuitMod() );
			sectorPanel.setBoolAndReminder( HIDDEN_SECTOR, gameState.isSectorHiddenCrystalWorlds() );
			sectorPanel.setBoolAndReminder( HAZARDS_VISIBLE, gameState.areSectorHazardsVisible() );

			for (int i=0; i < cargoSlots.length; i++) {
				cargoPanel.getCombo(cargoSlots[i]).addItem( "" );
				cargoPanel.getCombo(cargoSlots[i]).addItem( "Weapons" );
				cargoPanel.getCombo(cargoSlots[i]).addItem( "-------" );
				for ( WeaponBlueprint weaponBlueprint : DataManager.get().getWeapons().values() ) {
					cargoPanel.getCombo(cargoSlots[i]).addItem( weaponBlueprint );
				}
				cargoPanel.getCombo(cargoSlots[i]).addItem( "" );
				cargoPanel.getCombo(cargoSlots[i]).addItem( "Drones" );
				cargoPanel.getCombo(cargoSlots[i]).addItem( "------" );
				for ( DroneBlueprint droneBlueprint : DataManager.get().getDrones().values() ) {
					cargoPanel.getCombo(cargoSlots[i]).addItem( droneBlueprint );
				}

				if ( gameState.getCargoIdList().size() > i ) {
					String cargoId = gameState.getCargoIdList().get(i);

					if ( DataManager.get().getWeapons().containsKey( cargoId ) ) {
						WeaponBlueprint weaponBlueprint = DataManager.get().getWeapon( cargoId );
						cargoPanel.getCombo(cargoSlots[i]).setSelectedItem( weaponBlueprint );
					}
					else if ( DataManager.get().getDrones().containsKey( cargoId ) ) {
						DroneBlueprint droneBlueprint = DataManager.get().getDrone( cargoId );
						cargoPanel.getCombo(cargoSlots[i]).setSelectedItem( droneBlueprint );
					}
				}
			}

			bossPanel.setBoolAndReminder( FLAGSHIP, gameState.isRebelFlagshipVisible() );
			bossPanel.setSliderAndReminder( FLAGSHIP_HOP, gameState.getRebelFlagshipHop() );
			bossPanel.setBoolAndReminder( FLAGSHIP_MOVING, gameState.isRebelFlagshipMoving() );
			bossPanel.setIntAndReminder( FLAGSHIP_BASE_TURNS, gameState.getRebelFlagshipBaseTurns() );

			boolean flagshipVisible = bossPanel.getBoolean(FLAGSHIP).isSelected();
			if ( !flagshipVisible ) {
				bossPanel.getSlider(FLAGSHIP_HOP).setValue( 0 );
				bossPanel.getBoolean(FLAGSHIP_MOVING).setSelected( false );
				bossPanel.getInt(FLAGSHIP_BASE_TURNS).setText( "0" );
			}
			bossPanel.getSlider(FLAGSHIP_HOP).setEnabled( flagshipVisible );
			bossPanel.getBoolean(FLAGSHIP_MOVING).setEnabled( flagshipVisible );
			bossPanel.getInt(FLAGSHIP_BASE_TURNS).setEnabled( flagshipVisible );

			unknownsPanel.setIntAndReminder( TOP_GAMMA, gameState.getUnknownGamma() );
			unknownsPanel.setIntAndReminder( TOP_DELTA, gameState.getUnknownDelta() );
			unknownsPanel.setIntAndReminder( TOP_EPSILON, gameState.getUnknownEpsilon() );
			unknownsPanel.setIntAndReminder( TOP_KAPPA, gameState.getUnknownKappa() );
			unknownsPanel.setIntAndReminder( TOP_MU, gameState.getUnknownMu() );
			unknownsPanel.setIntAndReminder( TOP_NU, gameState.getUnknownNu() );

			SavedGameParser.UnknownZeus zeus = gameState.getUnknownZeus();
			zeusEnabled = ( zeus != null );
			zeusPanel.getInt(ZEUS_EPSILON).setEnabled( zeusEnabled );
			zeusPanel.getInt(ZEUS_ZETA).setEnabled( zeusEnabled );
			zeusPanel.getBoolean(ZEUS_AUTOFIRE).setEnabled( zeusEnabled );
			zeusPanel.getInt(ZEUS_ETA).setEnabled( zeusEnabled );
			zeusPanel.getInt(ZEUS_IOTA).setEnabled( zeusEnabled );
			zeusPanel.getInt(ZEUS_KAPPA).setEnabled( zeusEnabled );

			if ( zeusEnabled ) {
				zeusPanel.setIntAndReminder( ZEUS_EPSILON, zeus.getUnknownEpsilon() );
				zeusPanel.setIntAndReminder( ZEUS_ZETA, (zeus.getUnknownZeta() != null ? zeus.getUnknownZeta().intValue() : 0) );
				zeusPanel.setBoolAndReminder( ZEUS_AUTOFIRE, zeus.getAutofire() );
				zeusPanel.setIntAndReminder( ZEUS_ETA, zeus.getUnknownEta() );
				zeusPanel.setIntAndReminder( ZEUS_IOTA, zeus.getUnknownIota() );
				zeusPanel.setIntAndReminder( ZEUS_KAPPA, zeus.getUnknownKappa() );
			}
		}

		this.repaint();
	}

	@SuppressWarnings("unchecked")
	public void updateGameState( SavedGameParser.SavedGameState gameState ) {
		SavedGameParser.ShipState shipState = gameState.getPlayerShipState();
		String newString = null;

		newString = sessionPanel.getInt(TOTAL_SHIPS_DEFEATED).getText();
		try { gameState.setTotalShipsDefeated(Integer.parseInt(newString)); }
		catch ( NumberFormatException e ) {}

		newString = sessionPanel.getInt(TOTAL_BEACONS).getText();
		try { gameState.setTotalBeaconsExplored(Integer.parseInt(newString)); }
		catch ( NumberFormatException e ) {}

		newString = sessionPanel.getInt(TOTAL_SCRAP).getText();
		try { gameState.setTotalScrapCollected(Integer.parseInt(newString)); }
		catch ( NumberFormatException e ) {}

		newString = sessionPanel.getInt(TOTAL_CREW_HIRED).getText();
		try { gameState.setTotalCrewHired(Integer.parseInt(newString)); }
		catch ( NumberFormatException e ) {}

		gameState.setDLCEnabled( sessionPanel.getBoolean(DLC).isSelected() );

		Object diffObj = sessionPanel.getCombo(DIFFICULTY).getSelectedItem();
		gameState.setDifficulty( (Difficulty)diffObj );

		newString = sessionPanel.getInt(TOP_BETA).getText();
		try { gameState.setUnknownBeta(Integer.parseInt(newString)); }
		catch ( NumberFormatException e ) {}

		newString = sectorPanel.getInt(SECTOR_TREE_SEED).getText();
		try { gameState.setSectorTreeSeed(Integer.parseInt(newString)); }
		catch ( NumberFormatException e ) {}

		// Set the current sector, and unvisit any tree breadcrumbs beyond it.
		int oneBasedSectorNum = sectorPanel.getSlider(SECTOR_NUMBER).getValue();
		List<Boolean> sectorList = gameState.getSectorList();
		gameState.setSectorNumber( oneBasedSectorNum-1 );
		for (int i=0, n=0; i < sectorList.size(); i++) {
			if ( sectorList.get(i).booleanValue() == true ) {
				n++;
				if ( n > oneBasedSectorNum )
					gameState.setSectorVisited( i, false );
			}
		}

		newString = sectorPanel.getInt(SECTOR_LAYOUT_SEED).getText();
		try { gameState.setSectorLayoutSeed(Integer.parseInt(newString)); }
		catch ( NumberFormatException e ) {}

		newString = sectorPanel.getInt(REBEL_FLEET_OFFSET).getText();
		try { gameState.setRebelFleetOffset(Integer.parseInt(newString)); }
		catch ( NumberFormatException e ) {}

		newString = sectorPanel.getInt(REBEL_FLEET_FUDGE).getText();
		try { gameState.setRebelFleetFudge(Integer.parseInt(newString)); }
		catch ( NumberFormatException e ) {}

		newString = sectorPanel.getInt(REBEL_PURSUIT_MOD).getText();
		try { gameState.setRebelPursuitMod(Integer.parseInt(newString)); }
		catch ( NumberFormatException e ) {}

		gameState.setSectorIsHiddenCrystalWorlds( sectorPanel.getBoolean(HIDDEN_SECTOR).isSelected() );
		gameState.setSectorHazardsVisible( sectorPanel.getBoolean(HAZARDS_VISIBLE).isSelected() );

		gameState.getCargoIdList().clear();
		for (int i=0; i < cargoSlots.length; i++) {
			Object cargoObj = cargoPanel.getCombo(cargoSlots[i]).getSelectedItem();
			if ( cargoObj instanceof WeaponBlueprint ) {
				gameState.addCargoItemId( ((WeaponBlueprint)cargoObj).getId() );
			}
			else if ( cargoObj instanceof DroneBlueprint ) {
				gameState.addCargoItemId( ((DroneBlueprint)cargoObj).getId() );
			}
		}

		boolean flagshipVisible = bossPanel.getBoolean(FLAGSHIP).isSelected();

		gameState.setRebelFlagshipVisible( flagshipVisible );
		if ( flagshipVisible ) {
			gameState.setRebelFlagshipHop( bossPanel.getSlider(FLAGSHIP_HOP).getValue() );
			gameState.setRebelFlagshipMoving( bossPanel.getBoolean(FLAGSHIP_MOVING).isSelected() );

			newString = bossPanel.getInt(FLAGSHIP_BASE_TURNS).getText();
			try { gameState.setRebelFlagshipBaseTurns(Integer.parseInt(newString)); }
			catch ( NumberFormatException e ) {}
		}
		else {
			gameState.setRebelFlagshipHop( 0 );
			gameState.setRebelFlagshipMoving( false );
			gameState.setRebelFlagshipBaseTurns( 0 );
		}

		newString = unknownsPanel.getInt(TOP_GAMMA).getText();
		try { gameState.setUnknownGamma(Integer.parseInt(newString)); }
		catch ( NumberFormatException e ) {}

		newString = unknownsPanel.getInt(TOP_DELTA).getText();
		try { gameState.setUnknownDelta(Integer.parseInt(newString)); }
		catch ( NumberFormatException e ) {}

		newString = unknownsPanel.getInt(TOP_EPSILON).getText();
		try { gameState.setUnknownEpsilon(Integer.parseInt(newString)); }
		catch ( NumberFormatException e ) {}

		newString = unknownsPanel.getInt(TOP_KAPPA).getText();
		try { gameState.setUnknownKappa(Integer.parseInt(newString)); }
		catch ( NumberFormatException e ) {}

		newString = unknownsPanel.getInt(TOP_MU).getText();
		try { gameState.setUnknownMu(Integer.parseInt(newString)); }
		catch ( NumberFormatException e ) {}

		newString = unknownsPanel.getInt(TOP_NU).getText();
		try { gameState.setUnknownNu(Integer.parseInt(newString)); }
		catch ( NumberFormatException e ) {}

		SavedGameParser.UnknownZeus zeus = gameState.getUnknownZeus();
		if ( zeus != null && zeusEnabled ) {
			newString = zeusPanel.getInt(ZEUS_EPSILON).getText();
			try { zeus.setUnknownEpsilon(Integer.parseInt(newString)); }
			catch ( NumberFormatException e ) {}

			newString = zeusPanel.getInt(ZEUS_ZETA).getText();
			try { zeus.setUnknownZeta( new Integer(Integer.parseInt(newString)) ); }
			catch ( NumberFormatException e ) {}

			zeus.setAutofire( zeusPanel.getBoolean(ZEUS_AUTOFIRE).isSelected() );

			newString = zeusPanel.getInt(ZEUS_ETA).getText();
			try { zeus.setUnknownEta(Integer.parseInt(newString)); }
			catch ( NumberFormatException e ) {}

			newString = zeusPanel.getInt(ZEUS_IOTA).getText();
			try { zeus.setUnknownIota(Integer.parseInt(newString)); }
			catch ( NumberFormatException e ) {}

			newString = zeusPanel.getInt(ZEUS_KAPPA).getText();
			try { zeus.setUnknownKappa(Integer.parseInt(newString)); }
			catch ( NumberFormatException e ) {}
		}
	}
}
