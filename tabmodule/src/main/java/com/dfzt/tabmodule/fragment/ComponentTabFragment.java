package com.dfzt.tabmodule.fragment;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;

public class ComponentTabFragment implements IComponent {
    @Override
    public String getName() {
        return "TabFragment";
    }

    @Override
    public boolean onCall(CC cc) {
        CC.sendCCResult(cc.getCallId(), CCResult.success("fragment", TabFragment.getNewInstance()));
        return true;
    }
}
