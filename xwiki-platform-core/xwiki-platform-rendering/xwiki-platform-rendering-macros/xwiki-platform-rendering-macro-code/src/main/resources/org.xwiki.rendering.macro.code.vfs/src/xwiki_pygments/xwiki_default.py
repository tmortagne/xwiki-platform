from pygments.style import Style
from pygments.styles.default import DefaultStyle
from pygments.token import Comment, Name, Operator

__all__ = ['XWikiStyle']

class XWikiStyle(Style):
  """
  Pygments default style, with a few colors darkened to reach 4.5:1 contrast (WCAG AA) on the #f5f5f5 box background.
  """

  name="xwiki"

  styles = DefaultStyle.styles.copy()
  styles[Comment] = "italic #3C7A7A"
  styles[Operator.Word] = "bold #A71FFC"
  styles[Name.Attribute] = "#677721"
  styles[Name.Decorator] = "#A71FFC"
