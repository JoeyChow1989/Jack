package com.imooc.festivalsms.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/30.
 */
public class FestivalLab {


    public static FestivalLab mInstance;

    private List<Festival> mFestivalList = new ArrayList<Festival>();
    private List<Msg> mMsgList = new ArrayList<Msg>();

    public FestivalLab() {
        mFestivalList.add(new Festival(1, "国庆节"));
        mFestivalList.add(new Festival(2, "中秋节"));
        mFestivalList.add(new Festival(3, "元旦"));
        mFestivalList.add(new Festival(4, "春节"));
        mFestivalList.add(new Festival(5, "端午节"));
        mFestivalList.add(new Festival(6, "七夕节"));
        mFestivalList.add(new Festival(7, "圣诞节"));
        mFestivalList.add(new Festival(8, "儿童节"));

        mMsgList.add(new Msg(1, 1,
                "如果我有100万我将送你999999，我有100万吗？没有！所以我只能用一毛钱发送个短信祝你国庆快乐！"));
        mMsgList.add(new Msg(2, 1,
                "灯火一盏，共剪西窗烛。薄酒一杯，把酒话桑麻。轻风一缕，温馨又飘逸。祝福一句，朴实又真诚。国庆长假，愿你笑口常开！"));
        mMsgList.add(new Msg(3, 1,
                "国庆国庆，普天同庆。喜气喜气，增添福气。娱乐娱乐，天天快乐。问候问候，为你守候。祝福祝福，添财添福！国庆节快乐！"));
        mMsgList.add(new Msg(4, 1,
                "国庆长假挺难得，畅享悠闲开心过，走亲访友叙叙旧，团圆聚会真情多，呼朋唤友不寂寞，短信一条送祝福，愿你国庆乐事多！"));
        mMsgList.add(new Msg(5, 1,
                "说忙不忙，说闲不闲，放飞心灵休闲；说近不近，说远不远，真心朋友思念；说多不多，说少不少，假日快乐绵绵！"));
        mMsgList.add(new Msg(6, 1,
                "国庆佳节将到，祝福提前报道。愿你加薪不加班，高兴又高薪。不发牢骚只发钱，不添烦恼只添福。想吃就吃，想睡就睡，收祝福收到合不拢嘴。"));
        mMsgList.add(new Msg(7, 1,
                "国庆节即将到来，不想给你太多关照，只送给你五千万：千万要快乐，千万要健康，千万要平安，千万要发财，千万不要忘记我。祝：国庆快乐！"));
        mMsgList.add(new Msg(8, 1,
                "值此十一佳节之际，祝福您：国庆、家庆，普天同庆；官源、财源，左右逢源；人缘、福缘，缘缘不断；情愿、心愿，愿愿随心。国庆快乐！"));
        mMsgList.add(new Msg(9, 1,
                "大红灯笼高高挂，国庆又到；金菊吐芳分外俏，你在丛中笑；烟花照亮半边天，共盼明天美好；一声问候一份情，其乐融融在今朝。恭祝国庆快乐！"));
        mMsgList.add(new Msg(10, 1,
                "就数你心善，我吃饭时你买单，我消费时你掏钱，形影不离我身边。这个假期，我看你瘦得很明显，皮包骨了我心酸。我的钱包啊，你能否再鼓一点？"));
        mMsgList.add(new Msg(11, 1,
                "转眼假期到头，没有华丽词藻，不抄袭别人的思考，只送上我真诚祝福和简单问好：神啊，请你保佑看短信的人幸福到老，有我祝福的日子永无烦恼！"));
        mMsgList.add(new Msg(12, 1,
                "国庆长假即将结束，鉴于你假日期间的优秀表现，本人特颁发给你六个奖项：长假幸福奖、开心微笑奖、平安出游奖、休息健康奖、祝福传递奖、万事顺利奖，“六”代表顺的意思，愿你事业顺利，爱情顺利，事事都顺利！"));
        mMsgList.add(new Msg(1, 2,
                "折月桂一缕香，愿你尽享快乐芬芳；裁月华一片霜，愿你笼罩好运之光；借月饼一块糖，愿你把甜蜜品尝；圈月宫一面墙，为你建造幸福天堂！中秋节到了，祈月送福思念淌，真心朋友情意长，衷心祝愿你花好月圆人安康，春华秋实家吉祥！"));
        mMsgList.add(new Msg(2, 2,
                "星光灿灿月儿圆，八月十五中秋来；喜上眉梢人欢喜，朵朵花儿开得艳；张灯结彩庆佳节，欢天喜地大团圆；团团围坐乐融融，推杯换盏展笑颜；欢聚一堂是福分，乐享盛世度中秋；送份祝福传情谊，点点心意在其间。提前祝你中秋快乐，心情舒畅！"));
        mMsgList.add(new Msg(3, 2,
                "中秋佳节众神祝福到，财神祝你财源滚滚来，爱神祝你爱情甜如蜜，食神祝你吃嘛嘛香，身体倍儿棒，幸运女神祝你笑口常开，好彩自然来，我祝你中秋佳节，人团圆，家美满，幸福万万年！"));
        mMsgList.add(new Msg(4, 2,
                "十五的月亮圆又圆，家家户户喜连连，喝酒赏月人团圆，日子过得比蜜甜，借此吉时送祝愿，盼你身康体又健，收获幸福一片片，如意总是围你转。中秋佳节，愿你合家欢乐，幸福美满！"));
        mMsgList.add(new Msg(5, 2,
                "八月十五中秋到，花好月圆人团圆；欢歌笑语诉不尽，开心无限乐逍遥；齐聚一堂乐融融，举杯同庆中秋节；赏花赏月品月饼，美哉乐哉更爽哉；送份祝福爽你心，中秋快乐爽歪歪；愿你惬意更舒爽，鸿福齐天好运来！"));
        mMsgList.add(new Msg(6, 2,
                "好月饼圆圆：家庭和睦团团圆圆。好月饼甜甜：生活幸福美美甜甜。好月饼绵绵：感情恩爱缠缠绵绵。好月饼亮亮：帅哥美女漂漂亮亮。真诚祝福，传递幸福，收下满心欢心，开启无量前途！提前祝你中秋节快乐！"));
        mMsgList.add(new Msg(7, 2,
                "中秋明月悬中天，全家老少庆团圆，其乐融融桌边坐，谈笑风生乐无边。传统佳节摆家宴，瓜果梨桃分外鲜，轻啖一口团圆饼，胜过王母蟠桃宴。人生难得几回欢，明月能有几回圆，若是亲人常聚首，此生不悔赛神仙。"));
        mMsgList.add(new Msg(8, 2,
                "中秋将至，我把甜蜜和美满做成馅，健康与幸福擀成皮，再裹上思念和祝福，然后将这款用心烘培出的月饼送给你，愿你爱情甜蜜生活美满，身体健康一生幸福！"));
        mMsgList.add(new Msg(9, 2,
                "一声中秋一片情，一次佳节一番景。朵朵桂花飘香来，缕缕月光把祝福采。中秋节就要到了，愿你团团圆圆的心情越来越豪迈，幸幸福福的河流永远都澎湃，快乐对你越来越崇拜，好运的鲜花天天为你戴。提前祝你中秋快乐！"));
        mMsgList.add(new Msg(10, 2,
                "中秋佳节还未到，短信祝福提前到，我祝你事圆情圆，人团圆，好山好水，好运来，生活美美满满，笑容更比月饼甜，提前祝你中秋节快乐！"));
        mMsgList.add(new Msg(11, 2,
                "初秋晚秋，不如这个中秋好。新月弯月，不如这个满月美。酒香肉香，不如颗颗月饼香。柔情热情，不如今天团圆情。花长草长，不如亲人平安长。湖深海深，不如我的祝福深。值此中秋来临之际，祝福你家庭和美，事业丰收，心情舒畅，健康常在，人生圆满！"));


    }

    public List<Festival> getFestivals() {
        return new ArrayList<Festival>(mFestivalList);
    }

    public Festival getFestivalById(int id) {
        for (Festival festival : mFestivalList) {
            if (festival.getId() == id) {
                return festival;
            }
        }
        return null;
    }

    public Msg getMsgsById(int id) {
        for (Msg msg : mMsgList) {
            if (msg.getId() == id) {
                return msg;
            }
        }
        return null;
    }

    public List<Msg> getMsgByFestivalId(int fesId) {

        List<Msg> msgs = new ArrayList<>();

        for (Msg msg : mMsgList) {
            if (msg.getFestivalId() == fesId) {
                msgs.add(msg);
            }
        }
        return msgs;
    }

    public static FestivalLab getmInstance() {

        if (mInstance == null) {
            synchronized (FestivalLab.class) {
                if (mInstance == null) {
                    mInstance = new FestivalLab();
                }
            }
        }
        return mInstance;
    }
}
