package controller;

import model.common.Budget;
import model.common.Buildable;

public interface ICallBack {
    public void updateGridSystem(Buildable buildable);
    public void updateBudgetPanel(Budget budget);
}
