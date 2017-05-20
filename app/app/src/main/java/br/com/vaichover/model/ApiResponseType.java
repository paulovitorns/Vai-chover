package br.com.vaichover.model;

import br.com.vaichover.R;
import br.com.vaichover.VaiChoverApp;

/**
 * © Copyright 2017.
 * Autor : Paulo Sales - paulovitorns@gmail.com
 */

public enum ApiResponseType {
    NO_LOCATION_AVAILABLE(R.drawable.ic_location_off_white_48dp, VaiChoverApp.getContext().getString(R.string.no_location), false),
    PERMISSION_DENIED(R.drawable.ic_location_off_white_48dp, VaiChoverApp.getContext().getString(R.string.permission_denied), true),
    EMPTY_STATE(R.drawable.ic_cloud_off_white_48dp, VaiChoverApp.getContext().getString(R.string.empty_state), true),
    SERVER_TIMEOUT(R.drawable.ic_access_time_white_48dp, VaiChoverApp.getContext().getString(R.string.server_timeout), true),
    SERVER_ERROR(R.drawable.ic_cloud_off_white_48dp, VaiChoverApp.getContext().getString(R.string.server_error), true),
    NO_INTENET_CONNECTION(R.drawable.ic_signal_wifi_off_white_48dp, VaiChoverApp.getContext().getString(R.string.no_connection), false);

    private int     iconResId;
    private String  msgError;
    private boolean tentarNovamente;

    /**
     * Construir um tipo o response do server ou erro
     * <p>
     *     Metodo usado para construir um novo objeto de erro ou resposta do servidor
     * </p>
     *
     * @param iconResId resource id do icone
     * @param strResId  resource id da string
     * @param tentarNovamente   se exibo o botão para tentar novamente.
     *
     */
    ApiResponseType(int iconResId, String strResId, boolean tentarNovamente){
        this.iconResId          = iconResId;
        this.msgError           = strResId;
        this.tentarNovamente    = tentarNovamente;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public boolean isTentarNovamente() {
        return tentarNovamente;
    }

    public void setTentarNovamente(boolean tentarNovamente) {
        this.tentarNovamente = tentarNovamente;
    }
}
