package fr.k2i.adbeback.webapp.state.enroll;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dimitri
 * Date: 15/01/13
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
public enum EnrollFlow {
    AGENCY {

        @Override
        public List<String> requiredFiles() {
            ArrayList<String> res = Lists.newArrayList(
                    AttachementFileId.CID.name(),
                    AttachementFileId.KBIS.name(),
                    AttachementFileId.RIB.name(),
                    AttachementFileId.STATUS.name(),
                    AttachementFileId.SIGN.name(),
                    AttachementFileId.LOGO.name()
            );
            return res;
        }

        @Override
        public String getType() {
            return "AGENCY";
        }

        @Override
        public List<String> availableFiles() {
            ArrayList<String> res = Lists.newArrayList(
                    AttachementFileId.CID.name(),
                    AttachementFileId.KBIS.name(),
                    AttachementFileId.RIB.name(),
                    AttachementFileId.STATUS.name(),
                    AttachementFileId.SIGN.name(),
                    AttachementFileId.LOGO.name()
            );
            return res;
        }
    },


    ADV {

        @Override
        public List<String> requiredFiles() {
            ArrayList<String> res = Lists.newArrayList(
                    AttachementFileId.CID.name(),
                    AttachementFileId.KBIS.name(),
                    AttachementFileId.RIB.name(),
                    AttachementFileId.STATUS.name(),
                    AttachementFileId.SIGN.name(),
                    AttachementFileId.LOGO.name()
            );
            return res;
        }

        @Override
        public String getType() {
            return "ADV";
        }

        @Override
        public List<String> availableFiles() {
            ArrayList<String> res = Lists.newArrayList(
                    AttachementFileId.CID.name(),
                    AttachementFileId.KBIS.name(),
                    AttachementFileId.RIB.name(),
                    AttachementFileId.STATUS.name(),
                    AttachementFileId.SIGN.name(),
                    AttachementFileId.LOGO.name()
            );
            return res;
        }
    },



    MEDIA {

        @Override
        public List<String> requiredFiles() {
            ArrayList<String> res = Lists.newArrayList(
                    AttachementFileId.CID.name(),
                    AttachementFileId.KBIS.name(),
                    AttachementFileId.RIB.name(),
                    AttachementFileId.STATUS.name(),
                    AttachementFileId.SIGN.name(),
                    AttachementFileId.LOGO.name()
            );
            return res;
        }

        @Override
        public String getType() {
            return "MEDIA";
        }

        @Override
        public List<String> availableFiles() {
            ArrayList<String> res = Lists.newArrayList(
                    AttachementFileId.CID.name(),
                    AttachementFileId.KBIS.name(),
                    AttachementFileId.RIB.name(),
                    AttachementFileId.STATUS.name(),
                    AttachementFileId.SIGN.name(),
                    AttachementFileId.LOGO.name()
            );
            return res;
        }
    }

        ;

    public abstract List<String> availableFiles();
    public abstract String getType();
    public abstract List<String> requiredFiles();

}
