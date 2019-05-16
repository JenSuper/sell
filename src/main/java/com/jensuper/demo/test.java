package com.jensuper.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jensuper.sell.VO.ResultVO;

import java.util.*;
import java.util.stream.Collectors;

public class test {
    public static void main123(String[] args) {
        List<vo> list = new ArrayList<>();
        List<Data> listRet = new ArrayList<>();
        list.add(new vo(1, "one", 0, 1));
        list.add(new vo(2, "two", 1, 2));
        list.add(new vo(3, "three", 2, 3));
        list.add(new vo(5, "three_1", 2, 3));
        list.add(new vo(4, "four", 3, 4));
        list.add(new vo(6, "four_1", 5, 4));
        Collections.reverse(list);

        List<vo> voList = list.stream().filter(e -> e.getLevel()==4).collect(Collectors.toList());
        Data data = new Data();
        for (vo vo : voList) {
            data  = new Data();
            data.setFour(vo.getName());
            getChild(vo, list, listRet,data);
        }
        System.out.println(listRet);
    }

    private static void getChild(vo currentVo, List<vo> listAll, List<Data> listRet,Data data) {
        if (currentVo.getLevel()==1) {
            set(data, currentVo);
            listRet.add(data);
            return;
        }
        for (vo voall : listAll) {
            if (currentVo.getPid() == voall.getId()) {
                set(data, voall);
                getChild(voall, listAll, listRet,data);
            }
        }
    }

    private static void set(Data data, vo vo) {
        if (vo.getLevel() == 1) {
            data.setOne(vo.getName());
        } else if (vo.getLevel() == 2) {
            data.setTwo(vo.getName());
        } else if (vo.getLevel() == 3) {
            data.setThree(vo.getName());
        } else if (vo.getLevel() == 4) {
            data.setFour(vo.getName());
        }
    }
    
   public static void main(String[] args) {
//       Map<String, Object> map = new HashMap<>();
//       map.put("code", 1);
//       map.put("msg", "测试");
//       map.put("data", "数据");
       Gson gson = new Gson();
//       System.out.println(gson.toJson(map));

       ResultVO resultVO = new ResultVO<>();
       resultVO.setCode(1);
       resultVO.setMsg("msg");
       resultVO.setData("data");


       Gson gsonP = new GsonBuilder().create();
       String s = gsonP.toJson(resultVO);
       System.out.println(s);
       ResultVO vo = gson.fromJson(s, new TypeToken<ResultVO>() {
       }.getType());
       System.out.println(vo);
   }
}
