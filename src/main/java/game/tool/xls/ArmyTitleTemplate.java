package game.tool.xls;

import java.util.HashMap;
import java.util.Map;

public class ArmyTitleTemplate implements KeySupport<Integer>{
	private int id_i;
	private String name_s;
	private int level_i;
	private int star_i;
	private int matchScore;
	private boolean multiStar;
	private int lastId;
	private int nextId;
	private int rewardId;
	private int nextSeasonId;
	
	private int gold_i;
	private int goodsId1_i;	
	private int num1_i;
	private int goodsId2_i;
	private int num2_i;
	private int goodsId3_i;
	private int num3_i;
	private int goodsId4_i;
	private int num4_i;
	private int goodsId5_i;
	private int num5_i;
	private Map<Integer, Integer> goodsMap = new HashMap<>();

	
	public void init(){
		put(goodsId1_i, num1_i, goodsMap);
		put(goodsId2_i, num2_i, goodsMap);
		put(goodsId3_i, num3_i, goodsMap);
		put(goodsId4_i, num4_i, goodsMap);
		put(goodsId5_i, num5_i, goodsMap);
	}
	
	private void put(int key, int value, Map<Integer, Integer> map){
		if(key <= 0 || value <= 0){
			return;
		}
		
		if(map.containsKey(key)){
			value += map.get(key);
		}
		map.put(key, value);
	}
	
	public Map<Integer, Integer> getGoodsMap(){
		return new HashMap<>(this.goodsMap);
	}
	
	@Override
	public Integer getKey(){
		return this.id_i;
	}
	public int getId_i() {
		return id_i;
	}
	public void setId_i(int id_i) {
		this.id_i = id_i;
	}
	public String getName_s() {
		return name_s;
	}
	public void setName_s(String name_s) {
		this.name_s = name_s;
	}
	public int getLevel_i() {
		return level_i;
	}
	public void setLevel_i(int level_i) {
		this.level_i = level_i;
	}
	public int getStar_i() {
		return star_i;
	}
	public void setStar_i(int star_i) {
		this.star_i = star_i;
	}
	public int getMatchScore() {
		return matchScore;
	}
	public void setMatchScore(int matchScore) {
		this.matchScore = matchScore;
	}
	public boolean isMultiStar() {
		return multiStar;
	}
	public void setMultiStar(boolean multiStar) {
		this.multiStar = multiStar;
	}
	public int getLastId() {
		return lastId;
	}
	public void setLastId(int lastId) {
		this.lastId = lastId;
	}
	public int getNextId() {
		return nextId;
	}
	public void setNextId(int nextId) {
		this.nextId = nextId;
	}
	public int getRewardId() {
		return rewardId;
	}
	public void setRewardId(int rewardId) {
		this.rewardId = rewardId;
	}
	public int getNextSeasonId() {
		return nextSeasonId;
	}
	public void setNextSeasonId(int nextSeasonId) {
		this.nextSeasonId = nextSeasonId;
	}

	public int getGold_i() {
		return gold_i;
	}

	public void setGold_i(int gold_i) {
		this.gold_i = gold_i;
	}

	public int getGoodsId1_i() {
		return goodsId1_i;
	}

	public void setGoodsId1_i(int goodsId1_i) {
		this.goodsId1_i = goodsId1_i;
	}

	public int getNum1_i() {
		return num1_i;
	}

	public void setNum1_i(int num1_i) {
		this.num1_i = num1_i;
	}

	public int getGoodsId2_i() {
		return goodsId2_i;
	}

	public void setGoodsId2_i(int goodsId2_i) {
		this.goodsId2_i = goodsId2_i;
	}

	public int getNum2_i() {
		return num2_i;
	}

	public void setNum2_i(int num2_i) {
		this.num2_i = num2_i;
	}

	public int getGoodsId3_i() {
		return goodsId3_i;
	}

	public void setGoodsId3_i(int goodsId3_i) {
		this.goodsId3_i = goodsId3_i;
	}

	public int getNum3_i() {
		return num3_i;
	}

	public void setNum3_i(int num3_i) {
		this.num3_i = num3_i;
	}

	public int getGoodsId4_i() {
		return goodsId4_i;
	}

	public void setGoodsId4_i(int goodsId4_i) {
		this.goodsId4_i = goodsId4_i;
	}

	public int getNum4_i() {
		return num4_i;
	}

	public void setNum4_i(int num4_i) {
		this.num4_i = num4_i;
	}

	public int getGoodsId5_i() {
		return goodsId5_i;
	}

	public void setGoodsId5_i(int goodsId5_i) {
		this.goodsId5_i = goodsId5_i;
	}

	public int getNum5_i() {
		return num5_i;
	}

	public void setNum5_i(int num5_i) {
		this.num5_i = num5_i;
	}
}
