package js;

import com.alibaba.fastjson.JSON;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2019/12/26 10:03
 * Created by Wangxuehuo
 * 实测实量检查项，计算得分js 算法
 */
public class MeasureRuleFormulaOne {

    private String ruleFormulaInit(){
        return "function calc(args){\n" +
                "  function trim(str) {return str.replace(/^(\\s|\\u00A0)+/,'').replace(/(\\s|\\u00A0)+$/,'')}\n" +
                "\n" +
                "  function allowRangeFunc(allow_range, design_value_reqd) {\n" +
                "    var reRange = /^([-+]?\\d*\\.?\\d+?)~([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "        reValue = /^([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "        ars = trim(allow_range).split(','),\n" +
                "        asserts = [];\n" +
                "    ars.forEach(function(ar, i){\n" +
                "      ar = trim(ar)\n" +
                "      if (!ar) {\n" +
                "        return\n" +
                "      }\n" +
                "      reRange.lastIndex = 0\n" +
                "      reValue.lastIndex = 0\n" +
                "      if (reRange.test(ar)) {\n" +
                "        reRange.lastIndex = 0\n" +
                "        var v = reRange.exec(ar),\n" +
                "            v1 = parseFloat(v[1]),\n" +
                "            v2 = parseFloat(v[2]);\n" +
                "        asserts.push(function(value){return value >= v1 && value <= v2})\n" +
                "      }else if (reValue.test(ar)) {\n" +
                "        reValue.lastIndex = 0\n" +
                "        var v = reValue.exec(ar),\n" +
                "            v1 = parseFloat(v[1]);\n" +
                "        asserts.push(function(value){return value == v1});\n" +
                "      }else{\n" +
                "        console.log('范围格式错误：'+ar)\n" +
                "        return\n" +
                "      }\n" +
                "    })\n" +
                "    return function(value, designValue){\n" +
                "      var deviation = value;\n" +
                "      if (design_value_reqd) {\n" +
                "        deviation -= designValue\n" +
                "      }\n" +
                "      for (var i in asserts) {\n" +
                "        if (asserts[i](deviation)) {\n" +
                "          return {allow: true, deviation: deviation}\n" +
                "        }\n" +
                "      }\n" +
                "      return {allow: false, deviation: deviation}\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "  var texture = args.texture,\n" +
                "      rule = {d: {'': allowRangeFunc('-5~8', true)}},\n" +
                "      d = args.d,\n" +
                "      ds = [d],\n" +
                "      ok_total = 0, total = 0, score = 0;\n" +
                "  ds.forEach(function(d, i){\n" +
                "    var allow_range = rule[d.key][texture];\n" +
                "    d.ok_total = 0, d.total = 0, d.seq = '', d.deviation = [];\n" +
                "    d.data.split(',').forEach(function(v, j){\n" +
                "      var r = allow_range(parseFloat(v), d.design_value);\n" +
                "      d.deviation.push(r.deviation)\n" +
                "      if (r.allow) {\n" +
                "        d.ok_total += 1\n" +
                "        d.seq += '1'\n" +
                "      }else{\n" +
                "        d.seq += '0'\n" +
                "      }\n" +
                "      d.total += 1\n" +
                "    })\n" +
                "    ok_total += d.ok_total\n" +
                "    total += d.total\n" +
                "    d.deviation = d.deviation.join(',')\n" +
                "  })\n" +
                "  if (total > 0) {\n" +
                "    score = ok_total * 100 / total\n" +
                "  }\n" +
                "  return {score: score, d: d}\n" +
                "}";
    }

    private String formulaJMCC(){
        return "function calc(args) {\n" +
                "        function trim(str) {\n" +
                "          return str.replace(/^(\\s|\\u00A0)+/, \"\").replace(/(\\s|\\u00A0)+$/, \"\");\n" +
                "        }\n" +
                "\n" +
                "        function allowRangeFunc(allow_range, design_value_reqd) {\n" +
                "          var reRange = /^([-+]?\\d*\\.?\\d+?)~([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "            reValue = /^([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "            ars = trim(allow_range).split(\",\"),\n" +
                "            asserts = [];\n" +
                "          ars.forEach(function(ar, i) {\n" +
                "            ar = trim(ar);\n" +
                "            if (!ar) {\n" +
                "              return;\n" +
                "            }\n" +
                "            reRange.lastIndex = 0;\n" +
                "            reValue.lastIndex = 0;\n" +
                "            if (reRange.test(ar)) {\n" +
                "              reRange.lastIndex = 0;\n" +
                "              var v = reRange.exec(ar),\n" +
                "                v1 = parseFloat(v[1]),\n" +
                "                v2 = parseFloat(v[2]);\n" +
                "              asserts.push(function(value) {\n" +
                "                return value >= v1 && value <= v2;\n" +
                "              });\n" +
                "            } else if (reValue.test(ar)) {\n" +
                "              reValue.lastIndex = 0;\n" +
                "              var v = reValue.exec(ar),\n" +
                "                v1 = parseFloat(v[1]);\n" +
                "              asserts.push(function(value) {\n" +
                "                return value == v1;\n" +
                "              });\n" +
                "            } else {\n" +
                "              console.log(\"范围格式错误：\" + ar);\n" +
                "              return;\n" +
                "            }\n" +
                "          });\n" +
                "          return function(value, designValue) {\n" +
                "            var deviation = value;\n" +
                "            if (design_value_reqd) {\n" +
                "              deviation -= designValue;\n" +
                "            }\n" +
                "            for (var i in asserts) {\n" +
                "              if (asserts[i](deviation)) {\n" +
                "                return { allow: true, deviation: deviation };\n" +
                "              }\n" +
                "            }\n" +
                "            return { allow: false, deviation: deviation };\n" +
                "          };\n" +
                "        }\n" +
                "\n" +
                "        var texture = args.texture,\n" +
                "          rule = { d: { \"\": allowRangeFunc(\"-10~10\", true) } },\n" +
                "          d = args.d,\n" +
                "          ds = [d],\n" +
                "          ok_total = 0,\n" +
                "          total = 0,\n" +
                "          score = 0;\n" +
                "        ds.forEach(function(d, i) {\n" +
                "          var allow_range = rule[d.key][texture];\n" +
                "          (d.ok_total = 0), (d.total = 0), (d.seq = \"\"), (d.deviation = []);\n" +
                "          d.data.split(\",\").forEach(function(v, j) {\n" +
                "            var val = v.split(\"-\");\n" +
                "            var design_value = val[1];\n" +
                "            var r = allow_range(parseFloat(val[0]), design_value);\n" +
                "            d.deviation.push(r.deviation);\n" +
                "            if (r.allow) {\n" +
                "              d.ok_total += 1;\n" +
                "              d.seq += \"1\";\n" +
                "            } else {\n" +
                "              d.seq += \"0\";\n" +
                "            }\n" +
                "            d.total += 1;\n" +
                "          });\n" +
                "          ok_total += d.ok_total;\n" +
                "          total += d.total;\n" +
                "          d.deviation = d.deviation.join(\",\");\n" +
                "        });\n" +
                "        if (total > 0) {\n" +
                "          score = (ok_total * 100) / total;\n" +
                "        }\n" +
                "        return { score: score, d: d };\n" +
                "      }";
    }

    private String formulaDBSPD(){
        return "function calc(args){\n" +
                "  var d = args.d, score = 0, max = undefined, min = undefined, data = [];\n" +
                "  d.ok_total = 0, d.total = 0, d.seq = '', d.deviation = [];\n" +
                "  d.data.split(',').forEach(function(v, i){\n" +
                "    data.push(parseFloat(v))\n" +
                "  })\n" +
                "  var max = Math.max.apply(null, data),\n" +
                "      min = Math.min.apply(null, data);\n" +
                "  if (max - min > 20 && max -min <= 25) {\n" +
                "    d.ok_total = 0\n" +
                "    d.total = data.length\n" +
                "    d.seq = (new Array(data.length + 1)).join('0')\n" +
                "    data.forEach(function(v){\n" +
                "      d.deviation.push(v-min)\n" +
                "    })\n" +
                "    d.deviation = d.deviation.join(',')\n" +
                "    return {score: 0, d: d}\n" +
                "  }\n" +
                "  if(max -min > 25) {\n" +
                "    d.ok_total = 0\n" +
                "    d.total = data.length\n" +
                "    d.seq = (new Array(data.length + 1)).join('0')\n" +
                "    data.forEach(function(v){\n" +
                "      d.deviation.push(v-min)\n" +
                "    })\n" +
                "    d.deviation = d.deviation.join(',')\n" +
                "    return {score: -1, d: d}\n" +
                "  }\n" +
                "  data.forEach(function(v, i){\n" +
                "    var deviation = v - min;\n" +
                "    d.deviation.push(deviation)\n" +
                "    if (deviation > 15) {\n" +
                "      d.seq += '0'\n" +
                "    }else{\n" +
                "      d.ok_total += 1\n" +
                "      d.seq += '1'\n" +
                "    }\n" +
                "    d.total += 1\n" +
                "  })\n" +
                "  if (d.total > 0) {\n" +
                "    score = d.ok_total * 100 / d.total\n" +
                "  }\n" +
                "  d.offset = d.deviation.join(',')\n" +
                "  return {score: score, d: d}\n" +
                "}";
    }

    private String formulaLBHD(){
        return "function calc(args){\n" +
                "  function trim(str) {return str.replace(/^(\\s|\\u00A0)+/,'').replace(/(\\s|\\u00A0)+$/,'')}\n" +
                "\n" +
                "  function allowRangeFunc(allow_range, design_value_reqd) {\n" +
                "    var reRange = /^([-+]?\\d*\\.?\\d+?)~([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "        reValue = /^([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "        ars = trim(allow_range).split(','),\n" +
                "        asserts = [];\n" +
                "    ars.forEach(function(ar, i){\n" +
                "      ar = trim(ar)\n" +
                "      if (!ar) {\n" +
                "        return\n" +
                "      }\n" +
                "      reRange.lastIndex = 0\n" +
                "      reValue.lastIndex = 0\n" +
                "      if (reRange.test(ar)) {\n" +
                "        reRange.lastIndex = 0\n" +
                "        var v = reRange.exec(ar),\n" +
                "            v1 = parseFloat(v[1]),\n" +
                "            v2 = parseFloat(v[2]);\n" +
                "        asserts.push(function(value){return value >= v1 && value <= v2})\n" +
                "      }else if (reValue.test(ar)) {\n" +
                "        reValue.lastIndex = 0\n" +
                "        var v = reValue.exec(ar),\n" +
                "            v1 = parseFloat(v[1]);\n" +
                "        asserts.push(function(value){return value == v1});\n" +
                "      }else{\n" +
                "        console.log('范围格式错误：'+ar)\n" +
                "        return\n" +
                "      }\n" +
                "    })\n" +
                "    return function(value, designValue){\n" +
                "      var deviation = value;\n" +
                "      if (design_value_reqd) {\n" +
                "        deviation -= designValue\n" +
                "      }\n" +
                "      for (var i in asserts) {\n" +
                "        if (asserts[i](deviation)) {\n" +
                "          return {allow: true, deviation: deviation}\n" +
                "        }\n" +
                "      }\n" +
                "      return {allow: false, deviation: deviation}\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "  var texture = args.texture,\n" +
                "      rule = {d: {'': allowRangeFunc('-5~10', true)}},\n" +
                "      d = args.d,\n" +
                "      ds = [d],\n" +
                "      ok_total = 0, total = 0, score = 0;\n" +
                "  ds.forEach(function(d, i){\n" +
                "    var allow_range = rule[d.key][texture];\n" +
                "    d.ok_total = 0, d.total = 0, d.seq = '', d.deviation = [];\n" +
                "    d.data.split(',').forEach(function(v, j){\n" +
                "      var r = allow_range(parseFloat(v), d.design_value);\n" +
                "      d.deviation.push(r.deviation)\n" +
                "      if(r.deviation <= -15){\n" +
                "         score = -1;\n" +
                "         ok_total = 0;\n" +
                "         d.seq += '0';\n" +
                "      }else{\n" +
                "         if (r.allow) {\n" +
                "        d.ok_total += 1\n" +
                "        d.seq += '1'\n" +
                "      }else{\n" +
                "        d.seq += '0'\n" +
                "      }\n" +
                "      }\n" +
                "      d.total += 1\n" +
                "    })\n" +
                "    ok_total += d.ok_total\n" +
                "    total += d.total\n" +
                "    d.deviation = d.deviation.join(',')\n" +
                "  })\n" +
                "  if (total > 0 && score != -1) {\n" +
                "    score = ok_total * 100 / total\n" +
                "  }\n" +
                "  return {score: score, d: d}\n" +
                "}";
    }

    private String formalZXPCKZ(){
        return "function calc(args){\n" +
                "  function trim(str) {return str.replace(/^(\\s|\\u00A0)+/,'').replace(/(\\s|\\u00A0)+$/,'')}\n" +
                "\n" +
                "  function allowRangeFunc(allow_range, design_value_reqd) {\n" +
                "    var reRange = /^([-+]?\\d*\\.?\\d+?)~([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "        reValue = /^([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "        ars = trim(allow_range).split(','),\n" +
                "        asserts = [];\n" +
                "    ars.forEach(function(ar, i){\n" +
                "      ar = trim(ar)\n" +
                "      if (!ar) {\n" +
                "        return\n" +
                "      }\n" +
                "      reRange.lastIndex = 0\n" +
                "      reValue.lastIndex = 0\n" +
                "      if (reRange.test(ar)) {\n" +
                "        reRange.lastIndex = 0\n" +
                "        var v = reRange.exec(ar),\n" +
                "            v1 = parseFloat(v[1]),\n" +
                "            v2 = parseFloat(v[2]);\n" +
                "        asserts.push(function(value){return value >= v1 && value <= v2})\n" +
                "      }else if (reValue.test(ar)) {\n" +
                "        reValue.lastIndex = 0\n" +
                "        var v = reValue.exec(ar),\n" +
                "            v1 = parseFloat(v[1]);\n" +
                "        asserts.push(function(value){return value == v1});\n" +
                "      }else{\n" +
                "        console.log('范围格式错误：'+ar)\n" +
                "        return\n" +
                "      }\n" +
                "    })\n" +
                "    return function(value, designValue){\n" +
                "      for (var i in asserts) {\n" +
                "        if (asserts[i](value)) {\n" +
                "          return {allow: true}\n" +
                "        }\n" +
                "      }\n" +
                "      return {allow: false}\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "   \n" +
                "  var range = \"0~5\";\n" +
                "  var texture = args.texture,\n" +
                "      d = args.d,\n" +
                "      ds = [d],\n" +
                "      ok_total = 0, total = 0, score = 0;\n" +
                "  if(d.design_value > 30 && d.design_value <= 60){\n" +
                "  range = \"0~10\"\n" +
                "  }\n" +
                "  if(d.design_value > 60 && d.design_value <= 90){\n" +
                "  range = \"0~15\"\n" +
                "  }\n" +
                "  if(d.design_value > 90 && d.design_value <= 120){\n" +
                "  range = \"0~20\"\n" +
                "  }\n" +
                "  if(d.design_value > 120 && d.design_value <= 150){\n" +
                "  range = \"0~25\"\n" +
                "  }\n" +
                "  var rule = {d: {'': allowRangeFunc(range, true)}};\n" +
                "  ds.forEach(function(d, i){\n" +
                "    var allow_range = rule[d.key][texture];\n" +
                "    d.ok_total = 0, d.total = 0, d.seq = '', d.deviation = [];\n" +
                "    d.data.split(',').forEach(function(v, j){\n" +
                "      var r = allow_range(parseFloat(v), d.design_value);\n" +
                "      if (r.allow) {\n" +
                "        d.ok_total += 1\n" +
                "        d.seq += '1'\n" +
                "      }else{\n" +
                "        d.seq += '0'\n" +
                "      }\n" +
                "      d.total += 1\n" +
                "    })\n" +
                "    ok_total += d.ok_total\n" +
                "    total += d.total\n" +
                "    d.deviation = d.deviation.join(',')\n" +
                "  })\n" +
                "  if (total > 0) {\n" +
                "    score = ok_total * 100 / total\n" +
                "  }\n" +
                "  return {score: score, d: d}\n" +
                "}";
    }

    private String formulaFZX(){
        return "function calc(args){\n" +
                "  var d = args.d, score = 0, max = undefined, min = undefined, data = [];\n" +
                "  d.ok_total = 0, d.total = 0, d.seq = '', d.deviation = [];\n" +
                "  d.data.split(',').forEach(function(v, i){\n" +
                "    data.push(parseFloat(v))\n" +
                "  })\n" +
                "  var max = Math.max.apply(null, data),\n" +
                "      min = Math.min.apply(null, data);\n" +
                "  if (max - min > 15 ) {\n" +
                "    d.ok_total = 0\n" +
                "    d.total = data.length\n" +
                "    d.seq = (new Array(data.length + 1)).join('0')\n" +
                "    data.forEach(function(v){\n" +
                "      d.deviation.push(v-min)\n" +
                "    })\n" +
                "    d.deviation = d.deviation.join(',')\n" +
                "    return {score: -1, d: d}\n" +
                "  }\n" +
                "  if(max -min > 10 && max -min <= 15) {\n" +
                "    d.ok_total = 0\n" +
                "    d.total = data.length\n" +
                "    d.seq = (new Array(data.length + 1)).join('0')\n" +
                "    data.forEach(function(v){\n" +
                "      d.deviation.push(v-min)\n" +
                "    })\n" +
                "    d.deviation = d.deviation.join(',')\n" +
                "    return {score: 0, d: d}\n" +
                "  }\n" +
                "   data.forEach(function(v){\n" +
                "      d.deviation.push(v-min)\n" +
                "    })\n" +
                "   d.ok_total = data.length\n" +
                "   d.total = data.length\n" +
                "   d.seq = (new Array(data.length + 1)).join('1')\n" +
                "   d.deviation = d.deviation.join(',')\n" +
                "  return {score: 100, d: d}\n" +
                "}";
    }

    private String formulaJGPC(){
        return "function calc(args){\n" +
                "  function trim(str) {return str.replace(/^(\\s|\\u00A0)+/,'').replace(/(\\s|\\u00A0)+$/,'')}\n" +
                "  function allowRangeFunc(allow_range, design_value_reqd) {\n" +
                "    var reRange = /^([-+]?\\d*\\.?\\d+?)~([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "        reValue = /^([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "        ars = trim(allow_range).split(','),\n" +
                "        asserts = [];\n" +
                "    ars.forEach(function(ar, i){\n" +
                "      ar = trim(ar)\n" +
                "      if (!ar) {\n" +
                "        return\n" +
                "      }\n" +
                "      reRange.lastIndex = 0\n" +
                "      reValue.lastIndex = 0\n" +
                "      if (reRange.test(ar)) {\n" +
                "        reRange.lastIndex = 0\n" +
                "        var v = reRange.exec(ar),\n" +
                "            v1 = parseFloat(v[1]),\n" +
                "            v2 = parseFloat(v[2]);\n" +
                "        asserts.push(function(value){return value >= v1 && value <= v2})\n" +
                "      }else if (reValue.test(ar)) {\n" +
                "        reValue.lastIndex = 0\n" +
                "        var v = reValue.exec(ar),\n" +
                "            v1 = parseFloat(v[1]);\n" +
                "        asserts.push(function(value){return value == v1});\n" +
                "      }else{\n" +
                "        console.log('范围格式错误：'+ar)\n" +
                "        return\n" +
                "      }\n" +
                "    })\n" +
                "    return function(value, designValue){\n" +
                "      var deviation = value;\n" +
                "      if (design_value_reqd) {\n" +
                "        deviation -= designValue\n" +
                "      }\n" +
                "      for (var i in asserts) {\n" +
                "        if (asserts[i](deviation)) {\n" +
                "          return {allow: true, deviation: deviation}\n" +
                "        }\n" +
                "      }\n" +
                "      return {allow: false, deviation: deviation}\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "\n" +
                "  var d = args.d, score = 0, max = undefined, min = undefined, data = [];\n" +
                "  d.ok_total = 0, d.total = 0, design_value = d.design_value, d.seq = '', d.deviation = [];\n" +
                "  d.data.split(',').forEach(function(v, i){\n" +
                "    data.push(parseFloat(v))\n" +
                "  })\n" +
                "  var max = Math.max.apply(null, data),\n" +
                "      min = Math.min.apply(null, data);\n" +
                "  var maxValue = Math.abs(max - design_value)>Math.abs(min - design_value)?(max - design_value):(min - design_value)\n" +
                "  if (maxValue > 40 || maxValue < -30) {\n" +
                "    d.ok_total = 0\n" +
                "    d.total = data.length\n" +
                "    d.seq = (new Array(data.length + 1)).join('0')\n" +
                "    data.forEach(function(v){\n" +
                "      d.deviation.push(v-design_value)\n" +
                "    })\n" +
                "    d.deviation = d.deviation.join(',')\n" +
                "    return {score: 0, d: d}\n" +
                "  }\n" +
                "  if(maxValue >= -20 && maxValue <= 30) {\n" +
                "    d.ok_total = 0\n" +
                "    d.total = data.length\n" +
                "    d.seq = (new Array(data.length + 1)).join('1')\n" +
                "    data.forEach(function(v){\n" +
                "      d.deviation.push(v-design_value)\n" +
                "    })\n" +
                "    d.deviation = d.deviation.join(',')\n" +
                "    return {score: 100, d: d}\n" +
                "  }\n" +
                "\n" +
                "  var texture = args.texture,\n" +
                "      rule = {d: {'': allowRangeFunc('-20~30', true)}},\n" +
                "      ok_total = 0, total = 0, score = 0;\n" +
                "    var allow_range = rule[d.key][texture];\n" +
                "    d.data.split(',').forEach(function(v, j){\n" +
                "      var r = allow_range(parseFloat(v), d.design_value);\n" +
                "      d.deviation.push(r.deviation)\n" +
                "      if (r.allow) {\n" +
                "        d.ok_total += 1\n" +
                "        d.seq += '1'\n" +
                "      }else{\n" +
                "        d.seq += '0'\n" +
                "      }\n" +
                "      d.total += 1\n" +
                "    })\n" +
                "    ok_total += d.ok_total\n" +
                "    total += d.total\n" +
                "    d.deviation = d.deviation.join(',')\n" +
                "  if (total > 0) {\n" +
                "    score = ok_total * 100 / total\n" +
                "  }\n" +
                "  return {score: score, d: d}\n" +
                "}";
    }

    private String formulaKJJSPC(){
        return "function calc(args){\n" +
                "  function trim(str) {return str.replace(/^(\\s|\\u00A0)+/,'').replace(/(\\s|\\u00A0)+$/,'')}\n" +
                "\n" +
                "  function allowRangeFunc(allow_range, design_value_reqd) {\n" +
                "    var reRange = /^([-+]?\\d*\\.?\\d+?)~([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "        reValue = /^([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "        ars = trim(allow_range).split(','),\n" +
                "        asserts = [];\n" +
                "    ars.forEach(function(ar, i){\n" +
                "      ar = trim(ar)\n" +
                "      if (!ar) {\n" +
                "        return\n" +
                "      }\n" +
                "      reRange.lastIndex = 0\n" +
                "      reValue.lastIndex = 0\n" +
                "      if (reRange.test(ar)) {\n" +
                "        reRange.lastIndex = 0\n" +
                "        var v = reRange.exec(ar),\n" +
                "            v1 = parseFloat(v[1]),\n" +
                "            v2 = parseFloat(v[2]);\n" +
                "        asserts.push(function(value){return value >= v1 && value <= v2})\n" +
                "      }else if (reValue.test(ar)) {\n" +
                "        reValue.lastIndex = 0\n" +
                "        var v = reValue.exec(ar),\n" +
                "            v1 = parseFloat(v[1]);\n" +
                "        asserts.push(function(value){return value == v1});\n" +
                "      }else{\n" +
                "        console.log('范围格式错误：'+ar)\n" +
                "        return\n" +
                "      }\n" +
                "    })\n" +
                "    return function(value, designValue){\n" +
                "      var deviation = value;\n" +
                "      if (design_value_reqd) {\n" +
                "        deviation -= designValue\n" +
                "      }\n" +
                "      for (var i in asserts) {\n" +
                "        if (asserts[i](deviation)) {\n" +
                "          return {allow: true, deviation: deviation}\n" +
                "        }\n" +
                "      }\n" +
                "      return {allow: false, deviation: deviation}\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "  var texture = args.texture,\n" +
                "      rule = {d: {'': allowRangeFunc('-15~15', true)}},\n" +
                "      d = args.d,\n" +
                "      ok_total = 0, total = 0, score = 0;\n" +
                "    var allow_range = rule[d.key][texture];\n" +
                "    d.ok_total = 0, d.total = 0, d.seq = '', d.deviation = [];\n" +
                "    d.data.split(',').forEach(function(v, j){\n" +
                "      var r = allow_range(parseFloat(v), d.design_value);\n" +
                "      d.deviation.push(r.deviation)\n" +
                "      if (r.allow) {\n" +
                "        d.ok_total += 1\n" +
                "        d.seq += '1'\n" +
                "      }else{\n" +
                "        d.seq += '0'\n" +
                "      }\n" +
                "      d.total += 1\n" +
                "    })\n" +
                "    ok_total += d.ok_total\n" +
                "    total += d.total\n" +
                "    d.deviation = d.deviation.join(',')\n" +
                "  if (ok_total < 2) {\n" +
                "    d.seq = (new Array(d.data.split(',').length + 1)).join('0')\n" +
                "    d.ok_total = 0\n" +
                "    return {score: 0, d: d}\n" +
                "  }\n" +
                "  if(d.data.split(',')[0] - d.data.split(',')[1] <= 15 && d.data.split(',')[0] - d.data.split(',')[1] >= -15){\n" +
                "    return {score: 100, d: d}\n" +
                "  }\n" +
                "  d.seq = (new Array(d.data.split(',').length + 1)).join('0')\n" +
                "  d.ok_total = 0\n" +
                "  return {score: 0, d: d}\n" +
                "}";
    }

    private String formulaDMSPDJC(){
        return "function calc(args){\n" +
                "  function trim(str) {return str.replace(/^(\\s|\\u00A0)+/,'').replace(/(\\s|\\u00A0)+$/,'')}\n" +
                "  function allowRangeFunc(allow_range, design_value_reqd) {\n" +
                "    var reRange = /^([-+]?\\d*\\.?\\d+?)~([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "        reValue = /^([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "        ars = trim(allow_range).split(','),\n" +
                "        asserts = [];\n" +
                "    ars.forEach(function(ar, i){\n" +
                "      ar = trim(ar)\n" +
                "      if (!ar) {\n" +
                "        return\n" +
                "      }\n" +
                "      reRange.lastIndex = 0\n" +
                "      reValue.lastIndex = 0\n" +
                "      if (reRange.test(ar)) {\n" +
                "        reRange.lastIndex = 0\n" +
                "        var v = reRange.exec(ar),\n" +
                "            v1 = parseFloat(v[1]),\n" +
                "            v2 = parseFloat(v[2]);\n" +
                "        asserts.push(function(value){return value >= v1 && value <= v2})\n" +
                "      }else if (reValue.test(ar)) {\n" +
                "        reValue.lastIndex = 0\n" +
                "        var v = reValue.exec(ar),\n" +
                "            v1 = parseFloat(v[1]);\n" +
                "        asserts.push(function(value){return value == v1});\n" +
                "      }else{\n" +
                "        console.log('范围格式错误：'+ar)\n" +
                "        return\n" +
                "      }\n" +
                "    })\n" +
                "    return function(value, designValue){\n" +
                "      var deviation = value;\n" +
                "      if (design_value_reqd) {\n" +
                "        deviation -= designValue\n" +
                "      }\n" +
                "      for (var i in asserts) {\n" +
                "        if (asserts[i](deviation)) {\n" +
                "          return {allow: true, deviation: deviation}\n" +
                "        }\n" +
                "      }\n" +
                "      return {allow: false, deviation: deviation}\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "\n" +
                "  var d = args.d, score = 0, max = undefined, min = undefined, data = [];\n" +
                "  d.ok_total = 0, d.total = 0, design_value = d.design_value, d.seq = '', d.deviation = [];\n" +
                "  d.data.split(',').forEach(function(v, i){\n" +
                "    data.push(parseFloat(v))\n" +
                "  })\n" +
                "  var max = Math.max.apply(null, data),\n" +
                "      min = Math.min.apply(null, data);\n" +
                "  var maxValue = Math.abs(max - design_value)>Math.abs(min - design_value)?(max - design_value):(min - design_value)\n" +
                "  if (max - min > 15) {\n" +
                "    d.ok_total = 0\n" +
                "    d.total = data.length\n" +
                "    d.seq = (new Array(data.length + 1)).join('0')\n" +
                "    data.forEach(function(v){\n" +
                "      d.deviation.push(v-design_value)\n" +
                "    })\n" +
                "    d.deviation = d.deviation.join(',')\n" +
                "    return {score: 0, d: d}\n" +
                "  }\n" +
                "  if(max -min <= 10) {\n" +
                "    d.ok_total = 0\n" +
                "    d.total = data.length\n" +
                "    d.seq = (new Array(data.length + 1)).join('1')\n" +
                "    data.forEach(function(v){\n" +
                "      d.deviation.push(v-design_value)\n" +
                "    })\n" +
                "    d.deviation = d.deviation.join(',')\n" +
                "    return {score: 100, d: d}\n" +
                "  }\n" +
                "\n" +
                "  var texture = args.texture,\n" +
                "      rule = {d: {'': allowRangeFunc('0~10', true)}},\n" +
                "      ok_total = 0, total = 0, score = 0;\n" +
                "    var allow_range = rule[d.key][texture];\n" +
                "    d.data.split(',').forEach(function(v, j){\n" +
                "      var r = allow_range(parseFloat(v), d.design_value);\n" +
                "      d.deviation.push(r.deviation)\n" +
                "      if (r.allow) {\n" +
                "        d.ok_total += 1\n" +
                "        d.seq += '1'\n" +
                "      }else{\n" +
                "        d.seq += '0'\n" +
                "      }\n" +
                "      d.total += 1\n" +
                "    })\n" +
                "    ok_total += d.ok_total\n" +
                "    total += d.total\n" +
                "    d.deviation = d.deviation.join(',')\n" +
                "  if (total > 0) {\n" +
                "    score = ok_total * 100 / total\n" +
                "  }\n" +
                "  return {score: score, d: d}\n" +
                "}";
    }

    private String formulaWSJTMHD(){
        return "function calc(args){ \n" +
                "  var d = args.d, design_value = d.design_value, score = 0, ok_total = 0, total = 0;\n" +
                "  d.ok_total = 0, d.total = 0, d.seq = '', d.deviation = [];\n" +
                "  d.data.split(',').forEach(function(v, i){\n" +
                "    if(v > design_value * 0.9){\n" +
                "      d.ok_total += 1\n" +
                "      d.seq += '1'\n" +
                "    }else {\n" +
                "      d.seq += '0'\n" +
                "    }\n" +
                "    d.total += 1\n" +
                "    d.deviation.push(v - design_value * 0.9)\n" +
                "  })\n" +
                "  ok_total += d.ok_total\n" +
                "  total += d.total\n" +
                "  d.deviation = d.deviation.join(',')\n" +
                "  if (total > 0) {\n" +
                "    score = ok_total * 100 / total\n" +
                "  }\n" +
                "  return {score: score, d: d}\n" +
                "}";
    }

    private String formulaZXPCXIN(){
        return "function calc(args){\n" +
                "  var range = \"5\";\n" +
                "  var texture = args.texture,\n" +
                "      d = args.d,\n" +
                "      score = 0;\n" +
                "  if(d.design_value > 30 && d.design_value <= 60){\n" +
                "  range = \"10\"\n" +
                "  }\n" +
                "  if(d.design_value > 60 && d.design_value <= 90){\n" +
                "  range = \"15\"\n" +
                "  }\n" +
                "  if(d.design_value > 90 && d.design_value <= 120){\n" +
                "  range = \"20\"\n" +
                "  }\n" +
                "  if(d.design_value > 120 && d.design_value <= 150){\n" +
                "  range = \"25\"\n" +
                "  }\n" +
                "  var x1 = d.data.split(',')[0],\n" +
                "      x2 = d.data.split(',')[1],\n" +
                "      y1 = d.data.split(',')[2],\n" +
                "      y2 = d.data.split(',')[3];\n" +
                "  var r1 = Math.abs(x1 - x2) < range;\n" +
                "  var r2 = Math.abs(y1 - y2) < range;\n" +
                "  if (r1 && r2) {\n" +
                "      d.ok_total = 4\n" +
                "      d.seq = '1111'\n" +
                "      score = 100\n" +
                "  }else{\n" +
                "      d.seq = '0000'\n" +
                "      score = 0 \n" +
                "  }\n" +
                "  if (Math.abs(x1 - x2) > range * 1.5 || Math.abs(y1 - y2) > range * 1.5) {\n" +
                "      score = -1 \n" +
                "  }\n" +
                "  d.total = 4\n" +
                "  return {score: score, d: d}\n" +
                "}";
    }

    private String formal1DIAN2TEST(){
        return "function calc(args) {\n" +
                "        function trim(str) {\n" +
                "          return str.replace(/^(\\s|\\u00A0)+/, \"\").replace(/(\\s|\\u00A0)+$/, \"\");\n" +
                "        }\n" +
                "\n" +
                "        function allowRangeFunc(allow_range, design_value_reqd) {\n" +
                "          var reRange = /^([-+]?\\d*\\.?\\d+?)~([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "            reValue = /^([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "            ars = trim(allow_range).split(\",\"),\n" +
                "            asserts = [];\n" +
                "          ars.forEach(function(ar, i) {\n" +
                "            ar = trim(ar);\n" +
                "            if (!ar) {\n" +
                "              return;\n" +
                "            }\n" +
                "            reRange.lastIndex = 0;\n" +
                "            reValue.lastIndex = 0;\n" +
                "            if (reRange.test(ar)) {\n" +
                "              reRange.lastIndex = 0;\n" +
                "              var v = reRange.exec(ar),\n" +
                "                v1 = parseFloat(v[1]),\n" +
                "                v2 = parseFloat(v[2]);\n" +
                "              asserts.push(function(value) {\n" +
                "                return value >= v1 && value <= v2;\n" +
                "              });\n" +
                "            } else if (reValue.test(ar)) {\n" +
                "              reValue.lastIndex = 0;\n" +
                "              var v = reValue.exec(ar),\n" +
                "                v1 = parseFloat(v[1]);\n" +
                "              asserts.push(function(value) {\n" +
                "                return value == v1;\n" +
                "              });\n" +
                "            } else {\n" +
                "              console.log(\"范围格式错误：\" + ar);\n" +
                "              return;\n" +
                "            }\n" +
                "          });\n" +
                "          return function(value, designValue) {\n" +
                "            var deviation = value;\n" +
                "            if (design_value_reqd) {\n" +
                "              deviation -= designValue;\n" +
                "            }\n" +
                "            for (var i in asserts) {\n" +
                "              if (asserts[i](deviation)) {\n" +
                "                return { allow: true, deviation: deviation };\n" +
                "              }\n" +
                "            }\n" +
                "            return { allow: false, deviation: deviation };\n" +
                "          };\n" +
                "        }\n" +
                "\n" +
                "        var texture = args.texture,\n" +
                "          rule = { d: { \"\": allowRangeFunc(\"-10~10\", true) } },\n" +
                "          d = args.d,\n" +
                "          ds = [d],\n" +
                "          ok_total = 0,\n" +
                "          total = 0,\n" +
                "          score = 0;\n" +
                "        ds.forEach(function(d, i) {\n" +
                "          var allow_range = rule[d.key][texture];\n" +
                "          (d.ok_total = 0), (d.total = 0), (d.seq = \"\"), (d.deviation = []);\n" +
                "          d.data.split(\",\").forEach(function(v, j) {\n" +
                "            var design_value = d.design_value.split(\",\")[0];\n" +
                "            if (d.total >= 2) {\n" +
                "              design_value = d.design_value.split(\",\")[1];\n" +
                "            }\n" +
                "            var r = allow_range(parseFloat(v), design_value);\n" +
                "            d.deviation.push(r.deviation);\n" +
                "            if (r.allow) {\n" +
                "              d.ok_total += 1;\n" +
                "              d.seq += \"1\";\n" +
                "            } else {\n" +
                "              d.seq += \"0\";\n" +
                "            }\n" +
                "            d.total += 1;\n" +
                "          });\n" +
                "          ok_total += d.ok_total;\n" +
                "          total += d.total;\n" +
                "          d.deviation = d.deviation.join(\",\");\n" +
                "        });\n" +
                "        if (total > 0) {\n" +
                "          score = (ok_total * 100) / total;\n" +
                "        }\n" +
                "        return { score: score, d: d };\n" +
                "      }";
    }

    private String formulaTEST(){
        return "function calc(args){\n" +
                "  function trim(str) {return str.replace(/^(\\s|\\u00A0)+/,'').replace(/(\\s|\\u00A0)+$/,'')}\n" +
                "  function allowRangeFunc(allow_range, design_value_reqd) {\n" +
                "    var reRange = /^([-+]?\\d*\\.?\\d+?)~([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "        reValue = /^([-+]?\\d*\\.?\\d+?)$/g,\n" +
                "        ars = trim(allow_range).split(','),\n" +
                "        asserts = [];\n" +
                "    ars.forEach(function(ar, i){\n" +
                "      ar = trim(ar)\n" +
                "      if (!ar) {\n" +
                "        return\n" +
                "      }\n" +
                "      reRange.lastIndex = 0\n" +
                "      reValue.lastIndex = 0\n" +
                "      if (reRange.test(ar)) {\n" +
                "        reRange.lastIndex = 0\n" +
                "        var v = reRange.exec(ar),\n" +
                "            v1 = parseFloat(v[1]),\n" +
                "            v2 = parseFloat(v[2]);\n" +
                "        asserts.push(function(value){return value >= v1 && value <= v2})\n" +
                "      }else if (reValue.test(ar)) {\n" +
                "        reValue.lastIndex = 0\n" +
                "        var v = reValue.exec(ar),\n" +
                "            v1 = parseFloat(v[1]);\n" +
                "        asserts.push(function(value){return value == v1});\n" +
                "      }else{\n" +
                "        console.log('范围格式错误：'+ar)\n" +
                "        return\n" +
                "      }\n" +
                "    })\n" +
                "    return function(value, designValue){\n" +
                "      var deviation = value;\n" +
                "      if (design_value_reqd) {\n" +
                "        deviation -= designValue\n" +
                "      }\n" +
                "      for (var i in asserts) {\n" +
                "        if (asserts[i](deviation)) {\n" +
                "          return {allow: true, deviation: deviation}\n" +
                "        }\n" +
                "      }\n" +
                "      return {allow: false, deviation: deviation}\n" +
                "    }\n" +
                "  }\n" +
                "  var d = args.d, score = 0, max = undefined, min = undefined, data = [];\n" +
                "  d.ok_total = 0, d.total = 0, design_value = d.design_value, d.seq = '', d.deviation = [];\n" +
                "  d.data.split(',').forEach(function(v, i){\n" +
                "    data.push(parseFloat(v))\n" +
                "  })\n" +
                "  var max = Math.max.apply(null, data),\n" +
                "      min = Math.min.apply(null, data);\n" +
                "  if (max - design_value > 40 || min - design_value < -30) {\n" +
                "    d.ok_total = 0\n" +
                "    d.total = data.length\n" +
                "    d.seq = (new Array(data.length + 1)).join('0')\n" +
                "    data.forEach(function(v){\n" +
                "      d.deviation.push(v-design_value)\n" +
                "    })\n" +
                "    d.deviation = d.deviation.join(',')\n" +
                "    return {score: 0, d: d}\n" +
                "  }\n" +
                "  var texture = args.texture,\n" +
                "      rule = {d: {'': allowRangeFunc('-20~30', true)}},\n" +
                "      ok_total = 0, total = 0, score = 0;\n" +
                "    var allow_range = rule[d.key][texture];\n" +
                "    d.data.split(',').forEach(function(v, j){\n" +
                "      var r = allow_range(parseFloat(v), d.design_value);\n" +
                "      d.deviation.push(r.deviation)\n" +
                "      if (r.allow) {\n" +
                "        d.ok_total += 1\n" +
                "        d.seq += '1'\n" +
                "      }else{\n" +
                "        d.seq += '0'\n" +
                "      }\n" +
                "      d.total += 1\n" +
                "    })\n" +
                "    ok_total += d.ok_total\n" +
                "    total += d.total\n" +
                "    d.deviation = d.deviation.join(',')\n" +
                "  if (total > 0) {\n" +
                "    score = ok_total * 100 / total\n" +
                "  }\n" +
                "  return {score: score, d: d}\n" +
                "}";
    }

    private void ruleFormula() throws ScriptException, NoSuchMethodException {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("js");
        se.eval(formulaTEST());
        Invocable inv2 = (Invocable) se;
        Map<String,Object> args = argsInit();
        System.out.println(JSON.toJSONString(args));
        String res = JSON.toJSONString(inv2.invokeFunction("calc", args));
        System.out.println(res);
    }

    private Map<String, Object> argsInit(){
        MeasureZonePointData npd = new MeasureZonePointData();
        npd.setKey("d");
        npd.setDataType(14);
        npd.setData("0,9");
        npd.setDesignValueReqd(true);
        npd.setDesignValue("5");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("texture", "");
        map.put("d",npd.toMap());
        return map;
    }

    public static void main(String[] args) throws ScriptException, NoSuchMethodException {
        MeasureRuleFormulaOne measureRuleFormulaOne = new MeasureRuleFormulaOne();
        measureRuleFormulaOne.ruleFormula();
    }
}
