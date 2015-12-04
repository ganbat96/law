package mn.artlab.law.view.mb.shuukhmn;

import java.util.List;

import javax.faces.event.ActionEvent;

import mn.artlab.law.view.util.ADFUtil;
import mn.artlab.law.view.util.DateUtil;
import mn.artlab.law.view.util.IDUtil;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

public class ShuukhmnSyncMBean {
    public ShuukhmnSyncMBean() {
        super();
    }

    public void sync(ActionEvent actionEvent) {
        sync(1, "irgenanhan");
    }

    private void sync(long typeId, String type) {
        int page = ShuukhmnUtil.retrievePageCount(type);
        if (page <= 0) {
            return;
        }

        DCIteratorBinding iter = ADFUtil.findIterator("ShDecisionRawVOIterator");
        ViewObject vo = iter.getViewObject();
        for (int p = page; p > 0; p --) {
            List<String> idList = ShuukhmnUtil.retrieveDecisionList(type, p);
            for (String id : idList) {
                Row row = vo.createRow();
                row.setAttribute("DecisionId",  IDUtil.next());
                row.setAttribute("ViewId",      id);
                row.setAttribute("CourtTypeId", typeId);
                row.setAttribute("CreatedDate", DateUtil.currentTimestamp());
            }

            break;
        }

        OperationBinding commit = ADFUtil.findOperation("Commit");
        commit.execute();
    }
}
