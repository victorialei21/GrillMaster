package com.exampl.grillmaster

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
class GrillMaster : ApplicationAdapter(){
    lateinit var batch : SpriteBatch
    lateinit var background : Texture
    var width = 0f
    var height = 0f
    var w = 0f
    var h = 0f
    lateinit var font : BitmapFont
    lateinit var patty: Array<Texture>
    var pattyState = 0
    var pattyWidth = 0f
    var pattyY = 0f
    override fun create() {
        super.create()
        batch = SpriteBatch()
        background = Texture("background.png")
        width = Gdx.graphics.width.toFloat()
        height = Gdx.graphics.height.toFloat()
        w = 0.5f * width
        h = 0.5f * height

        patty = arrayOf(Texture("raw.png"), Texture("medium.png"),
                        Texture("done.png"),Texture("burnt.png"))

        pattyWidth = patty[0].width.toFloat()
        pattyY = height
        font = BitmapFont()
        font.color = Color.WHITE
        font.data.setScale(10f)
    }//create

    override fun render() {
        super.render()
        batch.begin()

        batch.draw(background, 0f, 0f, width, height)
        batch.draw(patty[pattyState], w - pattyWidth / 2, pattyY/2 - 1002)

        batch.end()
    }//render
}//class GrillMaster
