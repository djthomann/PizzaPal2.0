package pizzapal.ui.view.app.mainmenu.submenu.settings;

import pizzapal.ui.components.TextButton;
import pizzapal.ui.view.app.mainmenu.submenu.SubMenuView;

public class ControlsView extends SubMenuView {

    private final TextButton settingsButton;

    protected ControlsView() {
        super("Controls", "Configure Key Bindings!", SubMenuPosition.BOTTOM_RIGHT);

        settingsButton = new TextButton("Settings");

        addControlOnTop(settingsButton);
    }

    public TextButton getSettingsButton() {
        return settingsButton;
    }

}
