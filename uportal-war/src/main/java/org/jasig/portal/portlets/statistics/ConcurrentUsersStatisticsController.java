package org.jasig.portal.portlets.statistics;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jasig.portal.events.aggr.AggregationInterval;
import org.jasig.portal.events.aggr.BaseAggregationDao;
import org.jasig.portal.events.aggr.concuser.ConcurrentUserAggregation;
import org.jasig.portal.events.aggr.concuser.ConcurrentUserAggregationDao;
import org.jasig.portal.events.aggr.concuser.ConcurrentUserAggregationKey;
import org.jasig.portal.events.aggr.concuser.ConcurrentUserAggregationKeyImpl;
import org.jasig.portal.events.aggr.groups.AggregatedGroupMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.google.visualization.datasource.base.TypeMismatchException;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.value.NumberValue;
import com.google.visualization.datasource.datatable.value.Value;
import com.google.visualization.datasource.datatable.value.ValueType;

/**
 * Concurrent User Reports
 * 
 * @author edalquist
 */
@Controller
@RequestMapping("VIEW")
public class ConcurrentUsersStatisticsController extends BaseStatisticsReportController<ConcurrentUserAggregation, ConcurrentUserAggregationKey, ConcurrentUserReportForm> {
    private static final String DATA_TABLE_RESOURCE_ID = "concurrentUserData";
    private final static String REPORT_NAME = "concurrent.users";

    @Autowired
    private ConcurrentUserAggregationDao<ConcurrentUserAggregation> concurrentUserAggregationDao;
    
    @RenderMapping(value="MAXIMIZED", params="report=" + REPORT_NAME)
    public String getConcurrentUserView() throws TypeMismatchException {
        return "jsp/Statistics/reportGraph";
    }
    
    @ResourceMapping(DATA_TABLE_RESOURCE_ID)
    public ModelAndView renderConcurrentUserAggregationReport(ConcurrentUserReportForm form) throws TypeMismatchException {
        return renderAggregationReport(form);
    }
    
    @Override
    protected ConcurrentUserReportForm createReportFormRequest() {
        return new ConcurrentUserReportForm();
        
        //TODO need smaller date range ....
    }

    @Override
    public String getReportName() {
        return REPORT_NAME;
    }

    @Override
    public String getReportDataResourceId() {
        return DATA_TABLE_RESOURCE_ID;
    }

    @Override
    protected BaseAggregationDao<ConcurrentUserAggregation, ConcurrentUserAggregationKey> getBaseAggregationDao() {
        return this.concurrentUserAggregationDao;
    }

    @Override
    protected ConcurrentUserAggregationKey createAggregationsQueryKey(Set<AggregatedGroupMapping> groups, ConcurrentUserReportForm form) {
        final AggregationInterval interval = form.getInterval();
        return new ConcurrentUserAggregationKeyImpl(interval, groups.iterator().next());
    }
    
    @Override
    protected List<ColumnDescription> getColumnDescriptions(AggregatedGroupMapping group, ConcurrentUserReportForm form) {
        final String groupName = group.getGroupName();
        return Collections.singletonList(new ColumnDescription(groupName, ValueType.NUMBER, groupName));
    }

    @Override
    protected List<Value> createRowValues(ConcurrentUserAggregation aggr, ConcurrentUserReportForm form) {
        final int concurrentUsers;
        if (aggr == null) {
            concurrentUsers = 0;
        }
        else {
            concurrentUsers = aggr.getConcurrentUsers();
        }
        
        return Collections.<Value>singletonList(new NumberValue(concurrentUsers));
    }
}
